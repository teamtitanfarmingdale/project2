package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.FireExplosion;

public class Enemy extends BaseEnemy {

	
	boolean healthBarLowered = false;
	
	public Enemy(Level l, String spriteFile) {
		super(l, spriteFile);
		
		collisionData.setActorType("Enemy");
		
		health = 30;
		startingHealth = health;
		
		explosion = new FireExplosion();
		
		shape = new PolygonShape();
		
		float vertices[] = {
				0, -42,
				-30, -8,
				-31, 20,
				-13, 42,
				18, 38,
				39, 12,
				30, -23
		};
		
		shape.set(vertices);	
		
	}

	
	@Override
	public void setDead(boolean dead) {
		super.setDead(dead);
		
		
		if(this.dead && !healthBarLowered) {
			healthBarLowered = true;
			float healthDrop = 0;
			try {
				healthDrop = (level.enemyHealthBar.MAX_HEALTH/level.enemySpawner.getMaxEnemies());
				//System.out.println(healthDrop);
				//System.out.println(level.enemySpawner.getMaxEnemies());
			}
			catch(Exception e) { }
			
			level.enemyHealthBar.lowerHealth(healthDrop);
		}
	}
	
	@Override
	public void lowerHealth(int damage) {
		super.lowerHealth(damage);
		getLevel().addAction(GeneralHelper.shakeSprite(getSprite()));
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
