package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.seniorproject.game.levels.Level;

public abstract class GameActor extends Actor {

	protected World actorWorld;
	protected BodyDef bodyDef;
	protected Body body;
	protected PolygonShape shape; 
	protected Fixture fixture;
	protected boolean dead;
	protected Sprite sprite;
	protected Texture texture;
	protected Level level;
	protected boolean died = false;
	
	public CollisionData collisionData;
	
	private boolean disposed = false;
	
	public GameActor(World world) {
		actorWorld = world;
		collisionData = new CollisionData(this, "");
		dead = false;
	}
	
	public void createBody() {

		if(getStage() != null && !isDead()) {
			
			level = (Level) getStage();
			
			float bodyXOffset = (getParent().getStage().getWidth()/2)-(sprite.getWidth()/2);
			float bodyYOffset = (getParent().getStage().getHeight()/2)-(sprite.getHeight()/2);
			
			// DESTROY THE CURRENT BODY IF THERE IS ONE
			if(body != null) {
				body.destroyFixture(fixture);
				actorWorld.destroyBody(body);
			}
			
			// CREATE A NEW BODY
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			bodyDef.position.set(sprite.getX()-bodyXOffset, sprite.getY()-bodyYOffset);
			
			shape = new PolygonShape();
			shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
			
			
			body = getWorld().createBody(bodyDef);
			fixture = body.createFixture(shape, 0f);
			fixture.setUserData(collisionData);
			body.resetMassData();
			shape.dispose();
		}
		else if(getStage() != null && isDead() && !died) {
			this.dispose();
			died = true;
			this.remove();
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);		
	}
	
	public World getWorld() {
		return actorWorld;
	}
	
	
	public void setWorld(World world) {
		actorWorld = world;
	}
	
	@Override
	public String toString() {
		return "General Actor";
	}
	
	public Fixture getFixture() {
		return fixture;
	}
	
	public Body getBody() {
		return body;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setupSprite(String spriteImageFile) {
		
		texture = new Texture(Gdx.files.internal(spriteImageFile));
		sprite = new Sprite(texture);
		
	}
	
	public void dispose() {
		if(!disposed) {
			disposed = true;
			body.destroyFixture(fixture);
			actorWorld.destroyBody(body);
			texture.dispose();
			System.out.println(this.getClass().getName()+" disposed");
		}
	}
	
	public CollisionData getCollisionData() {
		return collisionData;
	}
}
