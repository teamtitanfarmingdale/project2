package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Enemy extends GameActor {
	
	private int health = 100;
	private int randomX;
	Double randomNumber;
	private boolean reposition = false;
	
	private float movementDistance = 3f;
	private float movementSpeed = .01f;
	
	public Enemy(World world, String spriteFile) {
		super(world);
		
		setupSprite(spriteFile);
		collisionData.setActorType("Enemy");
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
			
			// Spawns the enemy at a random X location at the top of the screen
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

}
