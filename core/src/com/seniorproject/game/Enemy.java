package com.seniorproject.game;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Enemy extends GameActor {

	private int health = 100;
	private int randomX;
	Double randomNumber; // Used for random position
	private boolean reposition = false;
	
	private int collisionDamage = 25;
	private int hitAwardPoints = 5;
	private int killAwardPoints = 25;
	
	
	private float movementDistance = 2f;
	private float movementXDistance = 2f;
	private float movementYDistance = 2f;
	
	private float movementSpeed = .01f;
	
	float lastMoveTime = -1;
	
	float hoverOffset = 0;
	boolean hoverPhase = true;
	
	float hoverSpeed = 5;
	
	public Enemy(World world, String spriteFile) {
		super(world);
		
		setupSprite(spriteFile);
		collisionData.setActorType("Enemy");
		
		// Randomize movement speed
		movementDistance = (float) (Math.random() * 2f)+1f;
		
		movementXDistance = (float) (Math.random() * 2f)+1f;
		if(Math.random()*100 > 50) {
			movementXDistance *= -1;
		}
		
		
		movementSpeed = (float) (Math.random() * .01f)+.01f;
		
		hoverOffset = (float) (Math.random()*50f)+250;
		hoverSpeed = (float) (Math.random()*3f)+2f;
		

	}
	
	
	public void act(float delta) {
		super.act(delta);
		
		if(health <= 0) {
			level.score.addToScore(killAwardPoints);
			setDead(true);
		}
		else if(reposition) {
			
			movementXDistance = movementDistance;
			if(getSprite().getX() > (level.getWidth()/2)) {
				movementXDistance = Math.abs(movementDistance)*-1;
			}
			
			MoveByAction mba = new MoveByAction();
			mba.setAmount(movementXDistance, 0f);
			mba.setDuration(movementSpeed);
			addAction(mba);
			reposition = false;
			
			
		}
		else {
			
			movementYDistance = movementDistance;
			
			if(hoverPhase && getX() >= level.getShip().getX()-100 && getX() <= level.getShip().getX()+100) {
				hoverPhase = false;
				lastMoveTime = -1;
				System.out.println("same x");
			}
			
			
			
			
			if(getY() > (level.getHeight()-hoverOffset) || !hoverPhase) {
				// Enemy is moving down the screen
				if(!hoverPhase) {
					movementYDistance *= 3;
				}
				
				MoveByAction mba = new MoveByAction();
				mba.setAmount(0f, (movementYDistance*-1));
				mba.setDuration(movementSpeed);
				
				addAction(mba);
				addAction(Actions.moveTo(level.getShip().getX(), getSprite().getY(), hoverSpeed));
				
				
			}
			else {
				// Enemy is in hover mode following the player
				if(lastMoveTime == -1) {
					lastMoveTime = Spawner.getSeconds();
				}
				
				addAction(Actions.moveTo(level.getShip().getX(), getSprite().getY(), hoverSpeed));
				
			}
			
			
		}
		
		if(getY()+getHeight() < 0 && movementDistance > 0) {
			// Move enemy up
			
			movementDistance *= -1;
			//System.out.println("go up");
			
		}
		else if(getY()+getHeight() > level.getHeight()-hoverOffset && movementDistance < 0) {
			movementDistance *= -1;
			//System.out.println("go down");
			
			if(lastMoveTime == -1 && !hoverPhase) {
				lastMoveTime = Spawner.getSeconds();
				hoverPhase = true;
			}
			
		}
		

		if(hoverPhase && lastMoveTime != -1 && Spawner.getSeconds()-lastMoveTime > 5) {
			lastMoveTime = -1;
			hoverPhase = false;
		}
		//else if(!hoverPhase && lastMoveTime != -1 && Spawner.getSeconds()-lastMoveTime > 5) {
			
		//}
		
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
		level.score.addToScore(hitAwardPoints);
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
	
	public int getHitAwardPoints() {
		return hitAwardPoints;
	}
	
	public int getKillAwardPoints() {
		return killAwardPoints;
	}
	
	public void shoot() {
		
		
		
	}

}
