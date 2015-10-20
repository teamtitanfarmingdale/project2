package com.seniorproject.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Asteroid extends GameActor {
	
	private int health = 100;
	private int randomX;
	Double randomNumber;
	private boolean reposition = false;
	
	private float movementDistance = 2f;
	private float movementSpeed = .005f;
	
	private int collisionDamage = 10;
	
	public Asteroid(World world, String spriteFile) {
		super(world);
		
		setupSprite(spriteFile);
		collisionData.setActorType("Asteroid");
		
		// Randomize movement speed
		movementDistance = (float) (Math.random() * 2f)+1f;
		movementSpeed = (float) (Math.random() * .01f)+.01f;
	}
	
	
	public void act(float delta) {
		super.act(delta);
		
		if(health <= 0) {
			setDead(true);
		}
		else if(reposition) {
			
			MoveByAction mba = new MoveByAction();
			mba.setAmount(movementDistance, 0f);
			mba.setDuration(movementSpeed);
			addAction(mba);
			reposition = false;
		}
		else {
			
			MoveByAction mba = new MoveByAction();
			mba.setAmount(0f, -movementDistance);
			mba.setDuration(movementSpeed);
			addAction(mba);
		}
		
		
		if(getY()+getHeight() < 0) {
			this.remove();
			this.body.destroyFixture(this.fixture);
			//System.out.println("removed asteroid!");
		}
		
		
	}
	
	public void draw(Batch batch, float alpha) {
		getSprite().draw(batch);
	}
	
	
	@Override
	public void setStage(Stage stage) {
		super.setStage(stage);
		
		if(stage != null) {
			
			randomNumber = (Math.random()*(stage.getWidth()-getSprite().getWidth()));
			randomX = randomNumber.intValue();
			
			// Spawns the asteroid at a random X location at the top of the screen
			setBounds(randomX, (stage.getHeight()-(getSprite().getHeight()-100)), getSprite().getWidth(), getSprite().getHeight());
			
		}
		
	}
	
	@Override
	protected void positionChanged() {
		getSprite().setPosition(getX(), getY());
		createBody();
		super.positionChanged();
	}

	public void lowerHealth(int damage) {
		health -= damage;
		System.out.println(health);
	}
	
	public void reposition() {
		reposition = true;
	}
	
	public void setCollisionDamage(int damage) {
		collisionDamage = damage;
	}
	
	public int getCollisionDamage() {
		return collisionDamage;
	}

}
