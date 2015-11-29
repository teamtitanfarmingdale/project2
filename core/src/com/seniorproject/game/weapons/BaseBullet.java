package com.seniorproject.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.seniorproject.game.Asset;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.Ship;
import com.seniorproject.game.enemies.BaseEnemy;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.BaseExplosion;
import com.seniorproject.game.particles.ParticleInterface;

public class BaseBullet extends GameActor {

	protected Asset<ParticleInterface> particleInterface;
	
	protected int damage = 10;
	
	protected Texture bulletTexture;
	protected Sprite bulletSprite;
	
	// THE ENEMY THAT THE BULLET COLLIDED WITH
	protected GameActor collidedEnemy;
	
	
	// ACTUAL WIDTH OF BULLET WITHIN IMAGE
	protected float actualWidth = 10f;
	
	
	protected float bulletSpeed = .01f;
	protected float movementDistance = 10f;
	
	protected BaseExplosion collisionParticle;
	
	public BaseBullet(Level l) {
		super(l);
		bulletTexture = l.game.assetManager.getTexture("bullet.png");
		bulletSprite = new Sprite(bulletTexture);
		
		
		particleInterface = l.game.assetManager.getParticle("particles/bullet-collision.particle", "BaseExplosion");
		collisionParticle = (BaseExplosion) particleInterface.asset;
		//collisionData.setActorType("Bullet");
		
		shape = new PolygonShape();
		shape.setAsBox(actualWidth, bulletSprite.getHeight()/2);
		
	}
	
	public BaseBullet(Level l, float x, float y) {
		this(l);
		setBounds(x, y, bulletSprite.getWidth(), bulletSprite.getHeight());
	}
	
	public BaseBullet(Level l, String bulletImage) {
		this(l);
		bulletTexture = l.game.assetManager.getTexture(bulletImage);
		bulletSprite = new Sprite(bulletTexture);
	}
	
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		bulletSprite.draw(batch);
	}
	
	@Override 
	protected void positionChanged() {
		super.positionChanged();
		bulletSprite.setPosition(getX(), getY());
		createBody();
		// REMOVE THE BULLET ONCE IT IS OFF THE SCREEN
		if(getStage() != null) {
			if(getY() > getStage().getHeight()) {
				
				if(body != null) {
					body.destroyFixture(fixture);
				}
				
				this.remove();
			}
		}
		
		
	}
	
	public void destroyBullet() {
		body.destroyFixture(fixture);
		actorWorld.destroyBody(body);
		//bulletTexture.dispose();
	}


	@Override
	public void createBody() {

		if(getStage() != null && !isDead()) {
			
			level = (Level) getStage();
			
			// DESTROY THE CURRENT BODY IF THERE IS ONE
			if(body != null) {
				body.destroyFixture(fixture);
				actorWorld.destroyBody(body);
			}
			
			createBodyBoundry();
			
			body = getWorld().createBody(bodyDef);
			fixture = body.createFixture(shape, 0f);
			fixture.setUserData(collisionData);
			body.resetMassData();
			//shape.dispose();
		}
		else if(getStage() != null && isDead()) {
			destroyBullet();
			
			this.remove();
			if(collidedEnemy != null) {
				collidedEnemy.createBody();
			}
		}
	}
	
	@Override
	public String toString() {
		return "Bullet";
	}
	
	@Override
	protected void createBodyBoundry() {
		
		bodyXOffset = (getParent().getStage().getWidth()/2)-(bulletSprite.getWidth()/2);
		bodyYOffset = (getParent().getStage().getHeight()/2)-(bulletSprite.getHeight()/2);
		
		// CREATE A NEW BODY
		if(bodyDef == null) {
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bodyDef.position.set(bulletSprite.getX()-bodyXOffset, bulletSprite.getY()-bodyYOffset);
		
	}
	
	
	public void setCollidedEnemy(GameActor enemy) {
		
		if(enemy.getCollisionData().getActorType() == "Enemy" || enemy.getCollisionData().getActorType() == "Boss" || enemy.getCollisionData().getActorType() == "Asteroid") {
			BaseEnemy tempEnemy = (BaseEnemy) enemy;
			tempEnemy.lowerHealth(damage);
			
			level.addGameObject(collisionParticle);
			
		}
		else if(enemy.getCollisionData().getActorType() == "Ship") {
			Ship tempShip = (Ship) enemy;
			tempShip.hit(damage);
		}
		
		collisionParticle.start(this.getX()+(this.getWidth()/2), this.getY()+30);
		level.game.assetManager.releaseParticle(particleInterface, 2f);
		
		collidedEnemy = enemy;
	}
	
	public GameActor getCollidedEnemy() {
		return collidedEnemy;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
