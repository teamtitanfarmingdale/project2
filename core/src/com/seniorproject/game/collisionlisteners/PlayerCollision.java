package com.seniorproject.game.collisionlisteners;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.seniorproject.game.CollisionData;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.Ship;
import com.seniorproject.game.enemies.BaseEnemy;

public class PlayerCollision implements ContactListener {

	
	String contactObjectName1;
	String contactObjectName2;

	public PlayerCollision(String objectName1, String objectName2) {
		contactObjectName1 = objectName1;
		contactObjectName2 = objectName2;
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
		
		if(collisionDataA.getActorType() == contactObjectName1 && collisionDataB.getActorType() == contactObjectName2) {
			
			Ship collidedShip = (Ship) gameActorB;
			
			if(!collidedShip.hasCollidedWith(gameActorA)) {
				
				BaseEnemy collidedEnemy = (BaseEnemy) gameActorA;
				
				collidedShip.hit(collidedEnemy.getCollisionDamage());
				collidedShip.addCollidedObject(gameActorA);
				
				if(collisionDataA.getActorType() == contactObjectName1) {
					collidedEnemy.setDead(true);
				}
				
			}
			
		}
		else if(collisionDataA.getActorType() == contactObjectName1 && collisionDataB.getActorType() == contactObjectName2) {
			
			Ship collidedShip = (Ship) gameActorA;
			
			if(!collidedShip.hasCollidedWith(gameActorB)) {
				
				BaseEnemy collidedEnemy = (BaseEnemy) gameActorB;
				
				collidedShip.hit(collidedEnemy.getCollisionDamage());
				collidedShip.addCollidedObject(gameActorB);
				
				if(collisionDataB.getActorType() == contactObjectName2) {
					collidedEnemy.setDead(true);
				}
			
			}
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
