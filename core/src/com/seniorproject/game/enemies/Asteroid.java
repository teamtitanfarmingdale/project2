package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.SmokeExplosion;

public class Asteroid extends BaseEnemy {
	

	
	public Asteroid(Level l, String spriteFile) {
		super(l, spriteFile);
		
		setupSprite(spriteFile);
		collisionData.setActorType("Asteroid");
		
		// Randomize movement speed
		this.movementDistance = (float) (Math.random() * 2f)+1f;
		this.movementSpeed = (float) (Math.random() * .01f)+.01f;
		
		this.health = 50;
		this.startingHealth = health;
		this.collisionDamage = 35;
		this.killAwardPoints = 25;
		this.hitAwardPoints = 10;
		this.customMovement = true;
		
		particleInterface = l.game.assetManager.getParticle("SmokeExplosion");
		this.explosion = (SmokeExplosion) particleInterface.asset;
		
		shape = new PolygonShape();
		
		float vertices[] = {
				51, -27,
				41, 13,
				15, 41,
				-7, 52,
				-50, 15,
				-51, -11,
				-1, -52
		};
		
		shape.set(vertices);
		
		
	}
	
	@Override
	public void explode() {
		super.explode();
		level.game.assetManager.releaseParticle(particleInterface, SmokeExplosion.RELEASE_TIME);
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
		}
		
	}
	
	@Override
	protected void createBodyBoundry() {
		
		bodyXOffset = (getParent().getStage().getWidth()/2)-(sprite.getWidth()/2);
		bodyYOffset = (getParent().getStage().getHeight()/2)-(sprite.getHeight()/2);
		
		// CREATE A NEW BODY
		if(bodyDef == null) {
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bodyDef.position.set(sprite.getX()-bodyXOffset, sprite.getY()-bodyYOffset);
		

		
		body = getWorld().createBody(bodyDef);
		fixture = body.createFixture(shape, 0f);
		fixture.setUserData(collisionData);
		body.resetMassData();
		//shape.dispose();
	}
	

}
