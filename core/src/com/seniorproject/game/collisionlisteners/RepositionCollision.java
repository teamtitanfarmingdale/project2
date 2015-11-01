package com.seniorproject.game.collisionlisteners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.seniorproject.game.CollisionData;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.enemies.BaseEnemy;

public class RepositionCollision implements ContactListener {

	String collisionObjectName1;
	String collisionObjectName2;
	
	public RepositionCollision(String objectName1, String objectName2) {
		collisionObjectName1 = objectName1;
		collisionObjectName2 = objectName2;
	}
	
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
		// CollisionData contains the actual object that was collided.
		CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
		CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
		
		if(collisionDataA.getActorType() == collisionObjectName1 && collisionDataB.getActorType() == collisionObjectName2) {
			// MOVE ENEMIES THAT WERE COLLIDED
			
			//GameActor gameActorA = collisionDataA.getActor();
			GameActor gameActorB = collisionDataB.getActor();
			
			//Enemy enemyA = (Enemy) gameActorA;
			BaseEnemy enemyB = (BaseEnemy) gameActorB;

			enemyB.reposition();
			
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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
