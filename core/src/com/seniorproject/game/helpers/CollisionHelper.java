package com.seniorproject.game.helpers;

import java.util.ArrayList;
import com.badlogic.gdx.physics.box2d.Contact;
import com.seniorproject.game.BaseBullet;
import com.seniorproject.game.CollisionData;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.Ship;
import com.seniorproject.game.enemies.BaseEnemy;
import com.seniorproject.game.enemies.Enemy;

public class CollisionHelper {

	public static void repositionCollision(Contact contact, String objectName1, String objectName2) {
		
		// CollisionData contains the actual object that was collided.
		CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
		CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
		
		if(collisionDataA.getActorType() == objectName1 && collisionDataB.getActorType() == objectName2) {
			// MOVE ENEMIES THAT WERE COLLIDED
			
			//GameActor gameActorA = collisionDataA.getActor();
			GameActor gameActorB = collisionDataB.getActor();
			
			//Enemy enemyA = (Enemy) gameActorA;
			Enemy enemyB = (Enemy) gameActorB;

			enemyB.reposition();
			
		}
		
	}
	
	public static void bulletCollision(Contact contact, ArrayList<GameActor> collisionList, String objectName, String bulletName) {
		
		// CollisionData contains the actual object that was collided.
		CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
		CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
		
		
		GameActor gameActorA = collisionDataA.getActor();
		GameActor gameActorB = collisionDataB.getActor();
		
		
		// Collision for bullets and enemies:
		if(collisionDataA.getActorType() == objectName && collisionDataB.getActorType() == bulletName && !collisionList.contains(gameActorB)) {
			BaseBullet bullet = (BaseBullet) gameActorB;
			
			// Tell the bullet which enemy object it hit
			bullet.setCollidedEnemy(gameActorA);
			
			// Add the bullet to the collision list to be removed from the screen
			collisionList.add(gameActorB);			
		}
		else if(collisionDataA.getActorType() == bulletName && collisionDataB.getActorType() == objectName && !collisionList.contains(gameActorA)) {
			BaseBullet bullet = (BaseBullet) gameActorA;
			bullet.setCollidedEnemy(gameActorB);
			collisionList.add(gameActorA);			
		}
	}
	
	public static void playerCollision(Contact contact, String enemyName, boolean setDeadOnCollision) {
		
		// CollisionData contains the actual object that was collided.
		CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
		CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
		
		
		GameActor gameActorA = collisionDataA.getActor();
		GameActor gameActorB = collisionDataB.getActor();
		
		if(collisionDataA.getActorType() == enemyName && collisionDataB.getActorType() == "Ship") {
			
			Ship collidedShip = (Ship) gameActorB;
			
			if(!collidedShip.hasCollidedWith(gameActorA)) {
				
				BaseEnemy collidedEnemy = (BaseEnemy) gameActorA;
				
				collidedShip.hit(collidedEnemy.getCollisionDamage());
				collidedShip.addCollidedObject(gameActorA);
				
				if(collisionDataA.getActorType() == enemyName && setDeadOnCollision) {
					collidedEnemy.setDead(true);
				}
				
			}
			
		}
		else if(collisionDataA.getActorType() == "Ship" && collisionDataB.getActorType() == enemyName) {
			
			Ship collidedShip = (Ship) gameActorA;
			
			if(!collidedShip.hasCollidedWith(gameActorB)) {
				
				BaseEnemy collidedEnemy = (BaseEnemy) gameActorB;
				
				collidedShip.hit(collidedEnemy.getCollisionDamage());
				collidedShip.addCollidedObject(gameActorB);
				
				if(collisionDataB.getActorType() == enemyName && setDeadOnCollision) {
					collidedEnemy.setDead(true);
				}
			
			}
		}
		
	}
	
}
