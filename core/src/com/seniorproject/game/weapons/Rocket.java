package com.seniorproject.game.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.Asset;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.enemies.Enemy;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.BaseExplosion;
import com.seniorproject.game.particles.FireExplosion;
import com.seniorproject.game.particles.InfiniteParticle;
import com.seniorproject.game.particles.ParticleInterface;

public class Rocket extends BaseBullet {

	GameActor target;
	boolean targetSet = false;
	Float closestCoords;
	Float currentCoords;
	float angle;
	float targetX;
	float targetY;
	
	float bombSpeed = .75f;
	
	BaseExplosion explosion;
	InfiniteParticle thrust;
	
	Asset<ParticleInterface> thrustParticle;
	
	public Rocket(Level l) {
		super(l, "rocket.png");
		collisionData.setActorType("Bomb");
		damage = 50;
		
		particleInterface = l.game.assetManager.getParticle("particles/bomb-explosion.particle", "BaseExplosion");
		explosion = (BaseExplosion) particleInterface.asset;

		thrustParticle = l.game.assetManager.getParticle("particles/rocket.particle", "InfiniteParticle");
		thrust = (InfiniteParticle) thrustParticle.asset;
		thrust.getParticleEffect().reset();
		
		bulletSprite.setOriginCenter();
		this.setOrigin(Align.center);
		bulletSprite.setRotation(90);
		this.setDebug(true);
	}
	
	@Override
	public boolean remove() {
		super.remove();
		
		level.game.assetManager.releaseParticle(thrustParticle, 0f);
		thrust.stop();
		
		return true;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(targetSet && target != null) {
			
			if(!target.isDead()) {
				
				targetX = target.getX()+(target.getSprite().getWidth()/2);
				targetY = target.getY()+(target.getSprite().getHeight()/2);
				
				addAction(Actions.moveTo(targetX, targetY, bombSpeed));
				
				angle = (float) Math.atan2(targetY-bulletSprite.getY(), targetX-bulletSprite.getX());
				angle = (float) (angle * (180/Math.PI));
				bulletSprite.setRotation(angle);
			}
			else {
				targetSet = false;
			}
		}
		else {
			MoveByAction mba = new MoveByAction();
			mba.setAmount(0,  movementDistance);
			mba.setDuration(bulletSpeed);
			addAction(mba);
		}
		
		if(!targetSet && target == null) {
			target = getTarget();
			if(target != null) {
				targetSet = true;
			}
		}
	}
	
	
	private GameActor getTarget() {
		GameActor newTarget = null;
		
		// First check enemy list
		if(!level.enemySpawner.getItemList().isEmpty()) {
			for(GameActor e : level.enemySpawner.getItemList()) {
				
				if(closestCoords == null) {
					closestCoords = Math.abs(getX()-e.getX())+Math.abs(getY()-e.getY());
					newTarget = e;
				}
				
				currentCoords = Math.abs(getX()-e.getX())+Math.abs(getY()-e.getY());
				
				if(e != null && currentCoords < closestCoords) {
					closestCoords = currentCoords;
					newTarget = e;
				}
			}
		}
		
		
		// Then check boss
		if(newTarget == null) {
			closestCoords = null;
			if(level.enemySpawner.spawnedBoss && level.enemySpawner.boss != null) {
				newTarget = level.enemySpawner.boss;
			}
			
		}
		/*
		// Then check asteroids
		if(newTarget == null) {
			closestCoords = null;
			
			if(!level.asteroidSpawner.asteroidList.isEmpty()) {
				
				for(Asteroid e : level.asteroidSpawner.asteroidList) {
					
					if(closestCoords == null) {
						closestCoords = Math.abs(getX()-e.getX())+Math.abs(getY()-e.getY());
						newTarget = e;
					}
					
					currentCoords = Math.abs(getX()-e.getX())+Math.abs(getY()-e.getY());
					
					if(e != null && currentCoords < closestCoords) {
						closestCoords = currentCoords;
						newTarget = e;
					}
				}
				
			}
			
		}
		*/
		
		if(newTarget != null && newTarget.getY() < (level.ship.getY()+level.ship.getSprite().getHeight()+bulletSprite.getHeight())) {
			newTarget = null;
		}
		
		
		return newTarget;
	}
	
	@Override
	public void setDead(boolean dead) {
		
		if(!isDead() && dead) {
			// Explode
			explosion.start(getX(), getY());
			level.game.assetManager.releaseParticle(particleInterface, FireExplosion.RELEASE_TIME);
			level.addGameObject(explosion);
			level.game.assetManager.playSound("sounds/explosion-2.wav", 2f);
		}
		
		this.dead = dead;
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		//super.draw(batch, alpha);
		
		if(explosion.started()) {
			explosion.draw(batch, alpha);
		}
		else {
			thrust.start(getX()+(bulletSprite.getWidth()/2), getY()+(bulletSprite.getHeight()/2));
			thrust.draw(batch);
		}
		
		bulletSprite.draw(batch);
	}
	
}
