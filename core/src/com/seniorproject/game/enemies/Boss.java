package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.particles.FireExplosion;

public class Boss extends BaseEnemy {

	
	float lastShipCollision = -1;
	
	public Boss(World world, String spriteFile) {
		super(world, spriteFile);

		collisionData.setActorType("Boss");
	
		this.health = 300;
		
		this.hoverOffset = 0;
		this.shootInterval = .1f;
		this.diveRadius = 50;
		
		
		this.collisionDamage = 35;
		this.hitAwardPoints = 15;
		this.killAwardPoints = 50;
		this.movementSpeed = .001f;
		
		this.movementDistance = 2f;
		this.movementXDistance = 2f;
		this.hoverSpeed = 3f;
		
		this.explosion = new FireExplosion();
	}


	public float getLastShipCollision() {
		return lastShipCollision;
	}
	
	public void setLastShipCollision() {
		lastShipCollision = Spawner.getSeconds();
	}
	
	public void setLastShipCollision(float time) {
		lastShipCollision = time;
	}
	
	@Override
	public void setDead(boolean dead) {
		super.setDead(dead);
		
		ShooterGame.PLAYER_SCORE = level.score.getScore();
		
		level.asteroidSpawner.stop();
		level.levelFinished = true;
		level.ship.finishedAnimation();
		
		
		// BRING UP VICTORY SCREEN
		
		Timer.schedule(new Task() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				level.screen.game.switchScreen(ShooterGame.VICTORY);
			}
			
		}, 5f);
		
		
	}
	
	@Override
	public void lowerHealth(int damage) {
		super.lowerHealth(damage);
		
		level.enemyHealthBar.lowerHealth(damage);
		
	}
	
	
}
