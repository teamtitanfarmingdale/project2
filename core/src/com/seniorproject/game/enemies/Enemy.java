package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.Asset;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.FireExplosion;
import com.seniorproject.game.particles.ParticleInterface;

public class Enemy extends BaseEnemy {

	
	public boolean healthBarLowered = false;
	protected boolean clearedActions = false;
	
	public Enemy(Level l, String spriteFile) {
		super(l, spriteFile);
		
		collisionData.setActorType("Enemy");
		System.out.println("CREATED ENEMY");
		health = 30;
		startingHealth = health;
		
		particleInterface = l.game.assetManager.getParticle("FireExplosion");
		explosion = (FireExplosion) particleInterface.asset;
		
		
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
	public void explode() {
		super.explode();
		level.game.assetManager.releaseParticle(particleInterface, FireExplosion.RELEASE_TIME);
	}
	
	@Override
	public void setDead(boolean dead) {
		super.setDead(dead);
		lowerEnemyHealthBar();
	}
	
	public void lowerEnemyHealthBar() {
		if(this.dead && !healthBarLowered) {
			healthBarLowered = true;
			float healthDrop = 0;
			try {
				healthDrop = (level.enemyHealthBar.MAX_HEALTH/level.enemySpawner.getMaxItems());
				//System.out.println(healthDrop);
				//System.out.println(level.enemySpawner.getMaxEnemies());
			}
			catch(Exception e) { }
			
			level.enemyHealthBar.lowerHealth(healthDrop);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(hitByEMP) {

			if(!clearedActions) {
				this.clearActions();
				clearedActions = true;
			}
			
			fall();
		}
		
	}
	
	@Override
	public void lowerHealth(int damage) {
		super.lowerHealth(damage);
		if(damage > 0) {
			getLevel().addAction(GeneralHelper.shakeSprite(getSprite()));
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
	
	
	public void fall() {
		
		customMovement = true;
		hoverPhase = false;
		
		// Make enemy fall off screen
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0f, -10f);
		mba.setDuration(1f);
		addAction(mba);
	
		// Rotate as falling
		getSprite().rotate(5);
		
		
		
		if(getY()+getHeight() < 0) {
			level.score.addToScore(killAwardPoints);
			level.ship.totalKills++;
			this.silentDeath(true);
			lowerEnemyHealthBar();
			//System.out.println("removed asteroid!");
		}
		
	}
	
	
	
}
