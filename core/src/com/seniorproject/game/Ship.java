package com.seniorproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.enemies.Boss;
import com.seniorproject.game.enemies.Spawner;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.particles.EngineThrust;
import com.seniorproject.game.particles.FireExplosion;
import com.seniorproject.game.particles.InfiniteParticle;

public class Ship extends GameActor {

	private final static int MAX_HEALTH = 100;
	private final static int MAX_ARMOR = 100;
	
	
	boolean rightKeyPressed = false;
	boolean leftKeyPressed = false;
	boolean upKeyPressed = false;
	boolean downKeyPressed = false;
	
	float movementSpeed = .01f;
	float movementDistance = 10f;

	// SHIP MOVEMENT CONSTRAINTS
	float minX = 0;
	float maxX = 0;
	float minY = 0;
	float maxY = 0;
	
	public float health = 100;
	public float armor = 100;
	public float lives = 0;
	public float totalKills = 0;
	
	ArrayList<GameActor> collidedObjects;
	
	Sprite gasSprite;
	
	EngineThrust gasParticle;
	
	InfiniteParticle smokingIndicator;
	InfiniteParticle dangerIndicator;
	
	
	
	public Ship(World world) {
		super(world);
		
		setupSprite("ship.png");
		
		gasParticle = new EngineThrust();
		smokingIndicator = new InfiniteParticle("particles/smoking-indicator.particle");
		dangerIndicator = new InfiniteParticle("particles/danger-indicator.particle");
		
		collisionData.setActorType("Ship");
		setBounds(getSprite().getX(), 10, getSprite().getWidth(), getSprite().getHeight());
		setTouchable(Touchable.enabled);
		
		collidedObjects = new ArrayList<GameActor>();
		
		Texture gasTexture = new Texture(Gdx.files.internal("gas.png"));
		gasSprite = new Sprite(gasTexture);		
		
	}
	
	public void hit(float damage) {
		
		
		getLevel().addAction(GeneralHelper.shakeSprite(getSprite()));
		
		
		if(armor > 0) {
			armor -= damage;
			
			// Lower health by remaining damage
			if(armor < 0) {
				health += armor;
				armor = 0;
			}
			
		}
		else {
			health -= damage;
			
			if(health < 0) {
				health = 0;
			}
			
		}
		
		if(health == 0 && armor == 0 && lives > 0) {
			
			// EXPLODE
			this.explode();
			
			level.healthBar.lifeManager.removeLife();
			armor = MAX_ARMOR;
			health = MAX_HEALTH;
		}
		else if(health == 0 && armor == 0 && lives == 0) {
			// GAME OVER
			
			// EXPLODE
			this.explode();
			
			this.setDead(true);
			ShooterGame.PLAYER_SCORE = level.score.getScore();
			
			// TRIGGER GAME OVER SCREEN
			Timer.schedule(new Task() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					level.screen.game.switchScreen(ShooterGame.GAME_OVER);
				}
				
			}, 3);
			
		
		}
		
		
		level.armorBar.setHealth(armor);
		level.healthBar.setHealth(health);
	}
	
	public void addCollidedObject(GameActor actor) {
		if(collidedObjects.indexOf(actor) == -1) {
			collidedObjects.add(actor);
			
			if(actor.getCollisionData().getActorType() == "Boss") {
				Boss tempBoss = (Boss) actor;
				tempBoss.setLastShipCollision();
				
				// PLAY EXPLOSION NOISE
				Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion-2.wav"));
				wavSound.play(ShooterGame.SFX_VOLUME);
				GeneralHelper.disposeSound(wavSound, 2f);
			}
			
		}
	}
	
	public boolean hasCollidedWith(GameActor actor) {
		boolean returnVal = (collidedObjects.indexOf(actor) != -1);
		
		if(returnVal && actor.getCollisionData().getActorType() == "Boss") {
			Boss tempBoss = (Boss) actor;
			if(Spawner.getSeconds() - tempBoss.getLastShipCollision() > 1.5) {
				collidedObjects.remove(actor);
				returnVal = false;
			}
		}
				
		return returnVal;
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		
		createBody();
		
		if(gasParticle != null) {
			gasParticle.draw(batch);
			
			if(upKeyPressed || level.levelFinished) {
				//gasSprite.setPosition(getSprite().getX()+(gasSprite.getWidth()/2)-4, getSprite().getY()-8);
				//gasSprite.draw(batch);
				gasParticle.start(getSprite().getX()+28, getSprite().getY()+15);
			}
			else {
				gasParticle.reset();
				gasParticle.stop();
			}
		}
		
		if(dangerIndicator != null && health <= 40) {
			dangerIndicator.start(this.getX()+(this.getWidth()/2)+5, this.getY()+30);
			dangerIndicator.draw(batch);
		}
		else if(smokingIndicator != null && health <= 70) {
			smokingIndicator.start(this.getX()+(this.getWidth()/2)+5, this.getY()+30);
			smokingIndicator.draw(batch);
		}
		
		
		getSprite().draw(batch);
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);

		if(!level.levelFinished) {
			if(rightKeyPressed && getX() < maxX) {
				MoveByAction mba = new MoveByAction();
				mba.setAmount(movementDistance,  0f);
				mba.setDuration(movementSpeed);
				addAction(mba);
				
			}
			else if(leftKeyPressed && getX() > minX) {
				MoveByAction mba = new MoveByAction();
				mba.setAmount(movementDistance*-1,  0f);
				mba.setDuration(movementSpeed);
				addAction(mba);
			}
			
			if(upKeyPressed && getY() < maxY) {
				MoveByAction mba = new MoveByAction();
				mba.setAmount(0f, movementDistance);
				mba.setDuration(movementSpeed);
				addAction(mba);
			}
			else if(downKeyPressed && getY() > minY) {
				MoveByAction mba = new MoveByAction();
				mba.setAmount(0f,  movementDistance*-1);
				mba.setDuration(movementSpeed);
				addAction(mba);
			}
			
			if(!upKeyPressed && !downKeyPressed && getY() > minY) {
				MoveByAction mba = new MoveByAction();
				mba.setAmount(0f,  (movementDistance*-1)*.1f);
				mba.setDuration((movementSpeed*.1f));
				addAction(mba);
			}
		}
		
	}
	
	public void finishedAnimation() {
		MoveToAction mta = new MoveToAction();
		
		mta.setPosition((level.getWidth()/2)-(this.getWidth()/2), (level.getHeight()/2)-(this.getHeight()/2));
		mta.setDuration(3f);
		addAction(mta);
		
		final Ship tempShip = this;
		
		Timer.schedule(new Task() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MoveToAction mta = new MoveToAction();
				mta.setPosition((level.getWidth()/2)-(tempShip.getWidth()/2), level.getHeight()+tempShip.getHeight());
				mta.setDuration(1f);
				addAction(mta);
			}
			
		}, 3);
		
	}
	
	
	@Override
	protected void positionChanged() {
		getSprite().setPosition(getX(), getY());
		
		super.positionChanged();
	}
	
	
	@Override
	protected void setStage(Stage stage) {
		super.setStage(stage);

		// ADD BULLET SHOOTING LISTENER AFTER STAGE IS SET
		if(stage != null) {
			
			createBody();
			
			minX = 0;
			maxX = this.getStage().getWidth() - getWidth();
			
			minY = 10;
			maxY = this.getStage().getHeight() - (getHeight()*5);
			
			stage.addListener(new ClickListener() {
				
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					
					if(!level.screen.gamePaused && button == Input.Buttons.LEFT) {
						
						Bullet bullet = new Bullet(getWorld(), Ship.this.getX(), Ship.this.getY());
						
						bullet.setX(Ship.this.getX()+(Ship.this.getWidth()/2)-(bullet.getWidth()/2));
						bullet.setY(Ship.this.getY()+(Ship.this.getHeight()/2));

						level.addGameObject(bullet);
						
						
						createBody();				
					}
					
					
					return true;
				}
				
				
			});
			
			
			// MOVEMENT LISTENERS
			this.getParent().addListener(new InputListener() {
				
				@Override
				public boolean keyDown(InputEvent event, int keycode) {
					
					switch(keycode) {
						case Input.Keys.D:
							rightKeyPressed = true;
							break;
						case Input.Keys.A:
							leftKeyPressed = true;
							break;
						case Input.Keys.W:
							upKeyPressed = true;
							break;
						case Input.Keys.S:
							downKeyPressed = true;
							break;
					}
					
					return true;
				}
				
			});
			
			
			this.getParent().addListener(new InputListener() {
				
				@Override
				public boolean keyUp(InputEvent event, int keycode) {
					switch(keycode) {
					case Input.Keys.D:
						rightKeyPressed = false;
						break;
					case Input.Keys.A:
						leftKeyPressed = false;
						break;			
					case Input.Keys.W:
						upKeyPressed = false;
						break;
					case Input.Keys.S:
						downKeyPressed = false;
						break;
				}
				
					createBody();
					return true;
				}
				
			});
			
			
			
		}
	}
		
	@Override
	public void dispose() {
		super.dispose();
		
		if(gasParticle != null) {
			gasParticle.dispose();
			gasParticle = null;
		}
		
		if(smokingIndicator != null) {
			smokingIndicator.dispose();
			smokingIndicator = null;
		}
		
		if(dangerIndicator != null) {
			dangerIndicator.dispose();
			dangerIndicator = null;
		}
		
	}
	
	public void explode() {
		// EXPLODE
		FireExplosion explosion = new FireExplosion();
		level.addGameObject(explosion);
		explosion.start(this.getX(), this.getY());
	}
	
}
