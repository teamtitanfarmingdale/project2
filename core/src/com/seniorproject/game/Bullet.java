package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Bullet extends GameActor {

	private int damage = 10;
	
	private Texture bulletTexture = new Texture(Gdx.files.internal("bullet.png"));
	private Sprite bulletSprite = new Sprite(bulletTexture);
	private Enemy collidedEnemy;
	
	private float actualWidth = 10f;
	
	
	float bulletSpeed = .01f;
	float movementDistance = 10f;
	
	public Bullet(World world) {
		super(world);
		collisionData.setActorType("Bullet");
	}
	
	public Bullet(World world, float x, float y) {
		this(world);
		setBounds(x, y, bulletSprite.getWidth(), bulletSprite.getHeight());
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		bulletSprite.draw(batch);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0,  movementDistance);
		mba.setDuration(bulletSpeed);
		addAction(mba);
		
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
	}


	@Override
	public void createBody() {

		if(getStage() != null && !isDead()) {
			float bodyXOffset = (getParent().getStage().getWidth()/2)-(bulletSprite.getWidth()/2);
			float bodyYOffset = (getParent().getStage().getHeight()/2)-(bulletSprite.getHeight()/2);
			
			// DESTROY THE CURRENT BODY IF THERE IS ONE
			if(body != null) {
				body.destroyFixture(fixture);
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
			body.destroyFixture(fixture);
			this.remove();
			if(collidedEnemy != null) {
				collidedEnemy.createBody();
				System.out.println("fixed!");
			}
		}
	}
	
	@Override
	public String toString() {
		return "Bullet";
	}
	
	public void setCollidedEnemy(Enemy enemy) {
		enemy.lowerHealth(damage);
		collidedEnemy = enemy;
	}
	
	public Enemy getCollidedEnemy() {
		return collidedEnemy;
	}
	
	public int getDamage() {
		return damage;
	}
	
}
