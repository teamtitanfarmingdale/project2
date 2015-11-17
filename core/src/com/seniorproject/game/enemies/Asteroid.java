package com.seniorproject.game.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.particles.SmokeExplosion;

public class Asteroid extends BaseEnemy {
	

	
	public Asteroid(World world, String spriteFile) {
		super(world, spriteFile);
		
		setupSprite(spriteFile);
		collisionData.setActorType("Asteroid");
		
		// Randomize movement speed
		this.movementDistance = (float) (Math.random() * 2f)+1f;
		this.movementSpeed = (float) (Math.random() * .01f)+.01f;
		
		this.health = 50;
		this.collisionDamage = 35;
		this.killAwardPoints = 25;
		this.hitAwardPoints = 10;
		this.customMovement = true;
		
		this.explosion = new SmokeExplosion();
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(health <= 0) {
			level.score.addToScore(killAwardPoints);
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
			this.dispose();
			//System.out.println("removed asteroid!");
		}
		
		
	}
	

}
