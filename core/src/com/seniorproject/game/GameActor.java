package com.seniorproject.game;

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
	
	protected boolean hittable = true;
	
	public CollisionData collisionData;
	
	private boolean disposed = false;
	
	protected float bodyXOffset;
	protected float bodyYOffset;
	
	public GameActor(Level l) {
		
		level = l;
		actorWorld = l.world;
		collisionData = new CollisionData(this, "");
		dead = false;
	}
	
	public void createBody() {

		if(getStage() != null && !isDead()) {
			
			level = (Level) getStage();
			
			// DESTROY THE CURRENT BODY IF THERE IS ONE
			destroyBody();
			createBodyBoundry();

		}
		else if(getStage() != null && isDead() && !died) {
			this.dispose();
			died = true;
			this.remove();
		}

	}
	
	protected void createBodyBoundry() {
		
		bodyXOffset = (getParent().getStage().getWidth()/2)-(sprite.getWidth()/2);
		bodyYOffset = (getParent().getStage().getHeight()/2)-(sprite.getHeight()/2);
		
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
	
	protected void destroyBody() {
		if(body != null) {
			//ystem.out.println(this.getClass().getName());
			body.destroyFixture(fixture);
			actorWorld.destroyBody(body);
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
		
		if(level == null) {
			System.out.println("LEVEL NULL");
		}
		else if(level.game == null) {
			System.out.println("GAME NULL");
		}
		
		texture = level.game.assetManager.getTexture(spriteImageFile);
		sprite = new Sprite(texture);
		
	}
	
	public void dispose() {
		if(!disposed) {
			disposed = true;
			destroyBody();
			//texture.dispose();
			if(shape != null) {
				shape.dispose();
			}
		}
	}
	
	public boolean isDisposed() {
		return disposed;
	}
	
	public CollisionData getCollisionData() {
		return collisionData;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void hittable(boolean yesNo) {
		hittable = yesNo;
	}
		
}
