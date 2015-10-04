package com.seniorproject.game;

public class CollisionData {

	private GameActor actor;
	private String actorType;
	
	
	public CollisionData(GameActor actor, String actorType) {
		this.actor = actor;
		this.actorType = actorType;
	}
	
	public GameActor getActor() {
		return actor;
	}
	
	public String getActorType() {
		return actorType;
	}
	
	public void setActor(GameActor actor) {
		this.actor = actor;
	}
	
	public void setActorType(String actorType) {
		this.actorType = actorType;
	}
	
	@Override
	public String toString() {
		return actorType;
	}
	
}
