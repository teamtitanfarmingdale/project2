package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.FireExplosion;

public class Boss extends BaseEnemy {

	protected PolygonShape rightArmShape;
	protected PolygonShape leftAntennaShape;
	protected PolygonShape rightAntennaShape;
	protected PolygonShape centerBodyShape;
	
	float lastShipCollision = -1;

	public Boss(Level l, String spriteFile) {
		super(l, spriteFile);

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
		
		buildShape();
		
	}
	
	
	public void buildShape() {
		
		float leftArm[] = {
				-144, -83,
				-123, -86,
				-19, 81,
				-36, 87,
				-75, 75
		};
		
		float rightArm[] = {
				22, 77,
				121, -84,
				140, -84,
				73, 68,
				35, 86
		};
		
		float leftAntenna[] = {
				-33, -17,
				-52, -30,
				-51, -63,
				-38, -62,
				-37, -35,
				-20, -28,
				-7, -15,
				-19, -4
		};
		
		float rightAntenna[] = {
				22, -25,
				40, -33,
				39, -62,
				55, -63,
				57, -33,
				41, -17,
				22, -5,
				12, -15	
		};
		
		float centerBody[] = {
				-15, 6,
				-18, 80,
				20, 81,
				9, -16,
				9, -41,
				-6, -42,
				-4, -21
		};
		
		shape = new PolygonShape();
		shape.set(leftArm);
		
		rightArmShape = new PolygonShape();
		rightArmShape.set(rightArm);
		
		leftAntennaShape = new PolygonShape();
		leftAntennaShape.set(leftAntenna);
		
		rightAntennaShape = new PolygonShape();
		rightAntennaShape.set(rightAntenna);
		
		centerBodyShape = new PolygonShape();
		centerBodyShape.set(centerBody);
		
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
		level.ship.hittable(false);
		
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
	
	@Override
	protected void createBodyBoundry() {
		
		float bodyXOffset = (getParent().getStage().getWidth()/2)-(sprite.getWidth()/2);
		float bodyYOffset = (getParent().getStage().getHeight()/2)-(sprite.getHeight()/2);
		
		// CREATE A NEW BODY
		if(bodyDef == null) {
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bodyDef.position.set(sprite.getX()-bodyXOffset, sprite.getY()-bodyYOffset);
		body = getWorld().createBody(bodyDef);
		

		fixture = body.createFixture(shape, 0f);
		fixture.setUserData(collisionData);


		fixture = body.createFixture(rightArmShape, 0f);
		fixture.setUserData(collisionData);


		fixture = body.createFixture(leftAntennaShape, 0f);
		fixture.setUserData(collisionData);

		fixture = body.createFixture(rightAntennaShape, 0f);
		fixture.setUserData(collisionData);
	
		fixture = body.createFixture(centerBodyShape, 0f);
		fixture.setUserData(collisionData);
	
		body.resetMassData();
		//shape.dispose();
	}
	
	@Override
	protected void destroyBody() {
		if(body != null) {
			
			int fixtureCount = body.getFixtureList().size;
			//System.out.println(fixtureCount);
			
			for(int i = 0; i<fixtureCount; i++) {
				body.destroyFixture(body.getFixtureList().get(0));
				//System.out.println("Fixture Destroyed!");
			}
			
			actorWorld.destroyBody(body);
			
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		rightArmShape.dispose();
		leftAntennaShape.dispose();
		rightAntennaShape.dispose();
		centerBodyShape.dispose();
	}
	
}
