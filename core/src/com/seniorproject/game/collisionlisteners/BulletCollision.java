package com.seniorproject.game.collisionlisteners;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.seniorproject.game.CollisionData;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.enemies.Enemy;
import com.seniorproject.game.weapons.BaseBullet;
import com.seniorproject.game.weapons.Bullet;

public class BulletCollision implements ContactListener {

	String contactObjectName;
	//String contactObjectName2;
	ArrayList<GameActor> collisionList;
	
	
	public BulletCollision(String objectName, ArrayList<GameActor> list) {
		contactObjectName = objectName;
		collisionList = list;
	}
	
	
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
		// CollisionData contains the actual object that was collided.
		CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
		CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
		
		
		GameActor gameActorA = collisionDataA.getActor();
		GameActor gameActorB = collisionDataB.getActor();
		
		
		if(collisionDataA.getActorType() == contactObjectName && collisionDataB.getActorType() == "Bullet" && !collisionList.contains(gameActorB)) {
			BaseBullet bullet = (BaseBullet) gameActorB;
			
			// Tell the bullet which enemy object it hit
			bullet.setCollidedEnemy(gameActorA);
			
			// Add the bullet to the collision list to be removed from the screen
			collisionList.add(gameActorB);
			
		}
		else if(collisionDataA.getActorType() == "Bullet" && collisionDataB.getActorType() == contactObjectName && !collisionList.contains(gameActorA)) {
			BaseBullet bullet = (BaseBullet) gameActorA;
			bullet.setCollidedEnemy(gameActorB);
			collisionList.add(gameActorA);
			//System.out.println("BULLET COLLISION2!!!");
		}
		
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	
	
}
