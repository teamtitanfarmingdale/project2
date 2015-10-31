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
import com.seniorproject.game.hud.Armor;
import com.seniorproject.game.hud.Health;
import com.seniorproject.game.hud.LevelTitle;
import com.seniorproject.game.hud.Score;

public class Level extends Stage {

	public static float WORLD_STEP = (1 / 300f);

	private Ship ship;
	private LevelBackground background;
	private EnemySpawner enemySpawner;
	private AsteroidSpawner asteroidSpawner;
	private World world;
	private float accumulator = 0f;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private ArrayList<GameActor> collisionList;
	private LevelTitle levelTitle;
	private Group gameObjects;
	protected String enemySpriteFile = "enemy2.png";
	protected String asteroidSpriteFile = "asteroid.png";


	public Score score;
	public Health healthBar;
	public Armor armorBar;

	private Group hudObjects;

	public Level() {
		super(new ScreenViewport());

		// world is used for collisions, I believe its constructor sets the
		// gravity in our game using the Vector2 object
		// Since we don't need actual gravity in the game, its set to 0 here
		world = new World(new Vector2(0, 0), true);

		// The camera is used mostly for debugging the world to help show the
		// boxes around the sprites
		camera = new OrthographicCamera(ShooterGame.GAME_WIDTH,
				ShooterGame.GAME_HEIGHT);
		camera.position.set(ShooterGame.GAME_WIDTH / 2,
				ShooterGame.GAME_HEIGHT / 2, 0f);

		/*
		 * Group of actors for the stage They are grouped because you can only
		 * set one actor to take in keyboard input By grouping them, it lets you
		 * allow all the actors in the group to take keyboard input
		 */
		gameObjects = new Group();

		// Used to generate enemies onto the screen
		enemySpawner = new EnemySpawner(world, enemySpriteFile);

		asteroidSpawner = new AsteroidSpawner(world, asteroidSpriteFile);

		// The background
		background = new LevelBackground();

		// The player's ship
		ship = new Ship(world);

		levelTitle = new LevelTitle("Level 1");

		// Used for debugging, shows the boxes around the sprites
		renderer = new Box2DDebugRenderer();

		healthBar = new Health();
		armorBar = new Armor();
		score = new Score();

		healthBar.setPositionXOffsetWidth(score.getBGWidth());
		healthBar.setPositionYOffsetHeight(score.getBGHeight());

		armorBar.setPositionXOffsetWidth(score.getBGWidth());
		armorBar.setPositionYOffsetHeight(score.getBGHeight());

		levelTitle.setPositionXOffsetWidth(score.getBGWidth());
		levelTitle.setPositionYOffsetHeight(score.getBGHeight());

		hudObjects = new Group();
		hudObjects.addActor(healthBar);
		hudObjects.addActor(armorBar);
		hudObjects.addActor(levelTitle);
		hudObjects.addActor(score);
		hudObjects.addActor(levelTitle.getLabel());

		// Add the actors to the group
		gameObjects.addActor(enemySpawner);
		gameObjects.addActor(asteroidSpawner);
		gameObjects.addActor(background);
		gameObjects.addActor(ship);
		gameObjects.addActor(hudObjects);

		// Add the group to the stage
		addActor(gameObjects);

		// This allows the actors in the gameObjects group to take keyboard
		// input
		setKeyboardFocus(gameObjects);

		// Used to keep track of the bullets that have hit enemies
		collisionList = new ArrayList<GameActor>();

		// Sets up the collision detection
		collisionDetection();
	}

	public void addGameObject(Actor actor) {
		gameObjects.addActor(actor);
	}

	public void addHUDObject(Actor actor) {
		hudObjects.addActor(actor);
	}

	public Ship getShip() {
		return ship;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		// Bring the level title to the top so there is no overlap
		hudObjects.toFront();

		// World stepper?
		// Not sure exactly what this does, but it has something to do with how
		// the engine deals with collisions
		accumulator += delta;
		while (accumulator >= delta) {
			world.step(WORLD_STEP, 6, 2);
			accumulator -= WORLD_STEP;
		}

		// Sets bullets to being "dead" that collided into enemies
		if (!collisionList.isEmpty()) {
			for (GameActor actor : collisionList) {
				if (!actor.isDead()) {
					actor.setDead(true);
				}
			}
		}

	}

	@Override
	public void draw() {
		super.draw();

		// Comment this out to remove the boxes around the sprites
		// renderer.render(world, camera.combined);

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void collisionDetection() {

		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {

				// CollisionData contains the actual object that was collided.
				CollisionData collisionDataA = (CollisionData) contact
						.getFixtureA().getUserData();
				CollisionData collisionDataB = (CollisionData) contact
						.getFixtureB().getUserData();

				if (collisionDataA.getActorType() == "Enemy"
						&& collisionDataB.getActorType() == "Enemy") {
					// MOVE ENEMIES THAT WERE COLLIDED

					// GameActor gameActorA = collisionDataA.getActor();
					GameActor gameActorB = collisionDataB.getActor();

					// Enemy enemyA = (Enemy) gameActorA;
					Enemy enemyB = (Enemy) gameActorB;

					enemyB.reposition();
				}
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub

				// CollisionData contains the actual object that was collided.
				CollisionData collisionDataA = (CollisionData) contact
						.getFixtureA().getUserData();
				CollisionData collisionDataB = (CollisionData) contact
						.getFixtureB().getUserData();

				GameActor gameActorA = collisionDataA.getActor();
				GameActor gameActorB = collisionDataB.getActor();

				/*
				 * Handles collisions between Bullets and Enemies
				 * 
				 * When a collision event happens we are passed in a Contact
				 * object that has the 2 collided objects in FixtureA and
				 * FixtureB
				 * 
				 * Depending on the order in which the collision happens the
				 * bullet and enemy can be either FixtureA or FixtureB So the
				 * two if statements below handle either scenario
				 */
				if (collisionDataA.getActorType() == "Enemy"
						&& collisionDataB.getActorType() == "Bullet"
						&& !collisionList.contains(gameActorB)) {
					Bullet bullet = (Bullet) gameActorB;

					// Tell the bullet which enemy object it hit
					bullet.setCollidedEnemy((Enemy) gameActorA);

					// Add the bullet to the collision list to be removed from
					// the screen
					collisionList.add(gameActorB);

					// System.out.println("BULLET COLLISION1!!!");
				} else if (collisionDataA.getActorType() == "Bullet"
						&& collisionDataB.getActorType() == "Enemy"
						&& !collisionList.contains(gameActorA)) {
					Bullet bullet = (Bullet) gameActorA;
					bullet.setCollidedEnemy((Enemy) gameActorB);
					collisionList.add(gameActorA);
					// System.out.println("BULLET COLLISION2!!!");
				}

				if (collisionDataA.getActorType() == "Asteroid"
						&& collisionDataB.getActorType() == "Bullet"
						&& !collisionList.contains(gameActorB)) {
					Bullet bullet = (Bullet) gameActorB;

					// Tell the bullet which enemy object it hit
					bullet.setCollidedEnemy((Asteroid) gameActorA);

					// Add the bullet to the collision list to be removed from
					// the screen
					collisionList.add(gameActorB);
		
					// System.out.println("BULLET COLLISION1!!!");
				} else if (collisionDataA.getActorType() == "Bullet"
						&& collisionDataB.getActorType() == "Asteroid"
						&& !collisionList.contains(gameActorA)) {
					Bullet bullet = (Bullet) gameActorA;
					bullet.setCollidedEnemy((Asteroid) gameActorB);
					collisionList.add(gameActorA);
			
					// System.out.println("BULLET COLLISION2!!!");
				}

				// HANDLE COLLISIONS BETWEEN PLAYER AND ENEMY

				if (collisionDataA.getActorType() == "Enemy"
						&& collisionDataB.getActorType() == "Ship") {

					Ship collidedShip = (Ship) gameActorB;

					if (!collidedShip.hasCollidedWith(gameActorA)) {

						Enemy collidedEnemy = (Enemy) gameActorA;

						collidedShip.hit(collidedEnemy.getCollisionDamage());
						collidedShip.addCollidedObject(gameActorA);

						collidedEnemy.setDead(true);

						// set enemy explosion ~check if sip still has healt
						// left...if now blow up tooo
					}

				} else if (collisionDataA.getActorType() == "Ship"
						&& collisionDataB.getActorType() == "Enemy") {

					Ship collidedShip = (Ship) gameActorA;

					if (!collidedShip.hasCollidedWith(gameActorB)) {

						Enemy collidedEnemy = (Enemy) gameActorB;

						collidedShip.hit(collidedEnemy.getCollisionDamage());
						collidedShip.addCollidedObject(gameActorB);
						collidedEnemy.setDead(true);
					}
				}

				// HANDLE COLLISIONS BETWEEN PLAYER AND ASTEROID
				if (collisionDataA.getActorType() == "Asteroid"
						&& collisionDataB.getActorType() == "Ship") {

					Ship collidedShip = (Ship) gameActorB;

					if (!collidedShip.hasCollidedWith(gameActorA)) {

						Asteroid collidedEnemy = (Asteroid) gameActorA;

						collidedShip.hit(collidedEnemy.getCollisionDamage());
						collidedShip.addCollidedObject(gameActorA);
					}

				} else if (collisionDataA.getActorType() == "Ship"
						&& collisionDataB.getActorType() == "Asteroid") {

					Ship collidedShip = (Ship) gameActorA;

					if (!collidedShip.hasCollidedWith(gameActorB)) {

						Asteroid collidedEnemy = (Asteroid) gameActorB;

						collidedShip.hit(collidedEnemy.getCollisionDamage());
						collidedShip.addCollidedObject(gameActorB);
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

		});
	}
}
