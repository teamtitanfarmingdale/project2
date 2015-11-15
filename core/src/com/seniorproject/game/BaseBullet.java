package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.seniorproject.game.enemies.Asteroid;
import com.seniorproject.game.enemies.BaseEnemy;
import com.seniorproject.game.levels.Level;

public class BaseBullet extends GameActor {

	protected int damage = 10;
	
	protected Texture bulletTexture = new Texture(Gdx.files.internal("bullet.png"));
	protected Sprite bulletSprite = new Sprite(bulletTexture);
	
	// THE ENEMY THAT THE BULLET COLLIDED WITH
	protected GameActor collidedEnemy;
	
	
	// ACTUAL WIDTH OF BULLET WITHIN IMAGE
	protected float actualWidth = 10f;
	
	
	protected float bulletSpeed = .01f;
	protected float movementDistance = 10f;
	
	public BaseBullet(World world) {
		super(world);

		//collisionData.setActorType("Bullet");
	}
	
	public BaseBullet(World world, float x, float y) {
		this(world);
		setBounds(x, y, bulletSprite.getWidth(), bulletSprite.getHeight());
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
				body.destroyFixture(fixture);
				
				this.remove();
			}
		}
		
		
	}
	
	public void destroyBullet() {
		body.destroyFixture(fixture);
		actorWorld.destroyBody(body);
		bulletTexture.dispose();
	}


	@Override
	public void createBody() {

		if(getStage() != null && !isDead()) {
			
			level = (Level) getStage();
			
			float bodyXOffset = (getParent().getStage().getWidth()/2)-(bulletSprite.getWidth()/2);
			float bodyYOffset = (getParent().getStage().getHeight()/2)-(bulletSprite.getHeight()/2);
			
			// DESTROY THE CURRENT BODY IF THERE IS ONE
			if(body != null) {
				body.destroyFixture(fixture);
				actorWorld.destroyBody(body);
			}
			
			// CREATE A NEW BODY
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			bodyDef.position.set(bulletSprite.getX()-bodyXOffset, bulletSprite.getY()-bodyYOffset);
			
			shape = new PolygonShape();
			shape.setAsBox(actualWidth, bulletSprite.getHeight()/2);
			
			
			body = getWorld().createBody(bodyDef);
			fixture = body.createFixture(shape, 0f);
			fixture.setUserData(collisionData);
			body.resetMassData();
			shape.dispose();
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
	
	public void setCollidedEnemy(GameActor enemy) {
		
		if(enemy.getCollisionData().getActorType() == "Enemy" || enemy.getCollisionData().getActorType() == "Boss" || enemy.getCollisionData().getActorType() == "Asteroid") {
			BaseEnemy tempEnemy = (BaseEnemy) enemy;
			tempEnemy.lowerHealth(damage);
		}
		else if(enemy.getCollisionData().getActorType() == "Ship") {
			Ship tempShip = (Ship) enemy;
			tempShip.hit(damage);
		}
		
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
