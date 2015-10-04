package com.seniorproject.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameActor extends Actor {

	protected World actorWorld;
	protected BodyDef bodyDef;
	protected Body body;
	protected PolygonShape shape; 
	protected Fixture fixture;
	protected boolean dead;
	public CollisionData collisionData;
	
	public GameActor(World world) {
		actorWorld = world;
		collisionData = new CollisionData(this, "");
		dead = false;
	}
	
	abstract public void createBody();
	
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
	
}
