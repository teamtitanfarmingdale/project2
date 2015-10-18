package com.seniorproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Level extends Stage {

	public static float WORLD_STEP = (1/300f);
	
	private Ship ship;
	private LevelBackground background;
	private EnemySpawner enemySpawner;
	private World world;
	private float accumulator = 0f;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private ArrayList<GameActor> collisionList;
	private LevelTitle levelTitle;
	private Group gameObjects;
	protected String enemySpriteFile = "enemy.png";
	
	
	public Level() {
		super(new ScreenViewport());	
		
		
		// test
		
		// world is used for collisions, I believe its constructor sets the gravity in our game using the Vector2 object
		// Since we don't need actual gravity in the game, its set to 0 here
		world = new World(new Vector2(0,0), true);	
		
		// The camera is used mostly for debugging the world to help show the boxes around the sprites
		camera = new OrthographicCamera(ShooterGame.GAME_WIDTH, ShooterGame.GAME_HEIGHT);
		camera.position.set(ShooterGame.GAME_WIDTH/2, ShooterGame.GAME_HEIGHT/2, 0f);

		/* 
		 * Group of actors for the stage
		 * They are grouped because you can only set one actor to take in keyboard input
		 * By grouping them, it lets you allow all the actors in the group to take keyboard input
		 */
		gameObjects = new Group();
		
		// Used to generate enemies onto the screen
		enemySpawner = new EnemySpawner(world, enemySpriteFile);
		
		// The background
		background = new LevelBackground();
		
		// The player's ship
		ship = new Ship(world);

		levelTitle = new LevelTitle("Level 1", 20);
		levelTitle.setPosition(this);

		// Used for debugging, shows the boxes around the sprites
		renderer = new Box2DDebugRenderer();
		
		
		// Add the actors to the group
		gameObjects.addActor(enemySpawner);
		gameObjects.addActor(background);
		gameObjects.addActor(ship);
		gameObjects.addActor(levelTitle.getLabel());
		
		
		
		// Add the group to the stage
		addActor(gameObjects);

		// This allows the actors in the gameObjects group to take keyboard input
		setKeyboardFocus(gameObjects);
		
		// Used to keep track of the bullets that have hit enemies
		collisionList = new ArrayList<GameActor>();
		
		// Sets up the collision detection
		collisionDetection();
		
	}
	
	public void addGameObject(Actor actor) {
		gameObjects.addActor(actor);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// Bring the level title to the top so there is no overlap
		levelTitle.getLabel().toFront();
		
		// World stepper?
		// Not sure exactly what this does, but it has something to do with how the engine deals with collisions
		accumulator += delta;
		while(accumulator >= delta) {
			world.step(WORLD_STEP, 6, 2);
			accumulator -= WORLD_STEP;
		}
		
		// Sets bullets to being "dead" that collided into enemies
		if(!collisionList.isEmpty()) {
			for(GameActor actor : collisionList) {
				if(!actor.isDead()) {
					actor.setDead(true);
				}
			}
		}
		
	}
	
	
	@Override
	public void draw() {
		super.draw();
		
		// Comment this out to remove the boxes around the sprites
		renderer.render(world, camera.combined);
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	public void collisionDetection() {
		
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				System.out.println("contact!");
				
				// CollisionData contains the actual object that was collided.
				CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
				CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
				
				if(collisionDataA.getActorType() == "Enemy" && collisionDataB.getActorType() == "Enemy") {
					// MOVE ENEMIES THAT WERE COLLIDED
					
					GameActor gameActorA = collisionDataA.getActor();
					GameActor gameActorB = collisionDataB.getActor();
					
					Enemy enemyA = (Enemy) gameActorA;
					Enemy enemyB = (Enemy) gameActorB;
					
					System.out.println(enemyA.getX() + " - " + enemyA.getY());
					System.out.println(enemyB.getX() + " - " + enemyB.getY());
					enemyB.reposition();
					
				}
			
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
				// CollisionData contains the actual object that was collided.
				CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
				CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
				
				
				GameActor gameActorA = collisionDataA.getActor();
				GameActor gameActorB = collisionDataB.getActor();
				
				
				/*
				 * Handles collisions between Bullets and Enemies
				 * 
				 * When a collision event happens we are passed in a Contact object that has the 2 collided objects in 
				 * FixtureA and FixtureB
				 * 
				 * Depending on the order in which the collision happens the bullet and enemy can be either FixtureA or FixtureB
				 * So the two if statements below handle either scenario
				 * 
				 */
				if(collisionDataA.getActorType() == "Enemy" && collisionDataB.getActorType() == "Bullet" && !collisionList.contains(gameActorB)) {
					Bullet bullet = (Bullet) gameActorB;
					
					// Tell the bullet which enemy object it hit
					bullet.setCollidedEnemy((Enemy) gameActorA);
					
					// Add the bullet to the collision list to be removed from the screen
					collisionList.add(gameActorB);
					
					System.out.println("BULLET COLLISION1!!!");
				}
				else if(collisionDataA.getActorType() == "Bullet" && collisionDataB.getActorType() == "Enemy" && !collisionList.contains(gameActorA)) {
					Bullet bullet = (Bullet) gameActorA;
					bullet.setCollidedEnemy((Enemy) gameActorB);
					collisionList.add(gameActorA);
					System.out.println("BULLET COLLISION2!!!");
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
			
		});
		
	}
	
}
