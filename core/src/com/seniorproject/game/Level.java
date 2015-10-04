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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.scenario.effect.impl.Renderer;

public class Level extends Stage {

	private Ship ship;
	private LevelBackground background;
	private EnemySpawner enemySpawner;
	private World world;
	private float accumulator = 0f;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private ArrayList<GameActor> collisionList;
	
	public Level() {
		super(new ScreenViewport());	
		 
		world = new World(new Vector2(0,0), true);
		camera = new OrthographicCamera(640, 800);
		camera.position.set(640/2, 800/2, 0f);
		
		Group gameObjects = new Group();
		
		enemySpawner = new EnemySpawner(world);
		background = new LevelBackground();
		ship = new Ship(world);
		
		renderer = new Box2DDebugRenderer();
		
		gameObjects.addActor(enemySpawner);
		gameObjects.addActor(background);
		gameObjects.addActor(ship);
		
		addActor(gameObjects);
		setKeyboardFocus(gameObjects);
		
		collisionList = new ArrayList<GameActor>();
		
		collisionDetection();
		
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		accumulator += delta;
				
		while(accumulator >= delta) {
			world.step(1/300f, 6, 2);
			accumulator -= (1/300f);
		}
		
		if(!collisionList.isEmpty()) {
			for(GameActor actor : collisionList) {
				actor.setDead(true);
			}
			
			collisionList.clear();
			
		}
		
	}
	
	
	@Override
	public void draw() {
		super.draw();
		
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
				//System.out.println(contact.getFixtureA().getUserData());
				//System.out.println(contact.getFixtureB().getUserData());
				
				
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
				CollisionData collisionDataA = (CollisionData) contact.getFixtureA().getUserData();
				CollisionData collisionDataB = (CollisionData) contact.getFixtureB().getUserData();
				
				
				GameActor gameActorA = collisionDataA.getActor();
				GameActor gameActorB = collisionDataB.getActor();
				
				if(collisionDataA.getActorType() == "Enemy" && collisionDataB.getActorType() == "Bullet") {
					Bullet bullet = (Bullet) gameActorB;
					bullet.setCollidedEnemy((Enemy) gameActorA);
					collisionList.add(gameActorB);
					
					//gameActorB.getBody().destroyFixture(gameActorB.getFixture());
					//gameActorB.remove();
					System.out.println("BULLET COLLISION!!!");
				}
				else if(collisionDataA.getActorType() == "Bullet" && collisionDataB.getActorType() == "Enemy") {
					Bullet bullet = (Bullet) gameActorA;
					bullet.setCollidedEnemy((Enemy) gameActorB);
					
					collisionList.add(gameActorA);
					//gameActorA.getBody().destroyFixture(gameActorA.getFixture());
					//gameActorA.remove();
					System.out.println("BULLET COLLISION!!!");
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
