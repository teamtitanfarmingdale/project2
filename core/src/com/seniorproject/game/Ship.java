package com.seniorproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.enemies.Boss;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.EngineThrust;
import com.seniorproject.game.particles.FireExplosion;
import com.seniorproject.game.particles.InfiniteParticle;
import com.seniorproject.game.particles.ParticleInterface;
import com.seniorproject.game.powerups.Powerup;
import com.seniorproject.game.weapons.Bullet;
import com.seniorproject.game.weapons.EMP;

public class Ship extends GameActor {

	public final static int MAX_HEALTH = 100;
	public final static int MAX_ARMOR = 100;

	boolean hasShield = false;
	long shieldStartTime = 0;
	float shieldOpacity = 0;
	Task shieldTimer;
	
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
	public float lives = 3;
	public float totalKills = 0;
	
	public int totalRockets = 3;
	public int totalEMP = 3;
	
	ArrayList<GameActor> collidedObjects;
	
	Sprite gasSprite;
	
	EngineThrust gasParticle;
	
	InfiniteParticle smokingIndicator;
	InfiniteParticle dangerIndicator;
	
	Asset<ParticleInterface> gasParticleInterface;
	Asset<ParticleInterface> smokingParticleInterface;
	Asset<ParticleInterface> dangerParticleInterface;
	
	
	
	Sprite shieldImage;
	
	PolygonShape shieldShape;
	
	public Ship(Level l) {
		super(l);
		
		setupSprite("ship.png");
		
		gasParticleInterface = level.game.assetManager.getParticle("EngineThrust");
		gasParticle = (EngineThrust) gasParticleInterface.asset; //new EngineThrust();
		
		smokingParticleInterface = level.game.assetManager.getParticle("particles/smoking-indicator.particle", "InfiniteParticle");
		smokingIndicator = (InfiniteParticle) smokingParticleInterface.asset; //new InfiniteParticle("particles/smoking-indicator.particle");
		
		dangerParticleInterface = level.game.assetManager.getParticle("particles/danger-indicator.particle", "InfiniteParticle");
		dangerIndicator = (InfiniteParticle) dangerParticleInterface.asset; //new InfiniteParticle("particles/danger-indicator.particle");
		
		collisionData.setActorType("Ship");
		setBounds(getSprite().getX(), 10, getSprite().getWidth(), getSprite().getHeight());
		setTouchable(Touchable.enabled);
		
		collidedObjects = new ArrayList<GameActor>();
		
		Texture gasTexture = l.game.assetManager.getTexture("gas.png");
		gasSprite = new Sprite(gasTexture);		
		
		Texture shieldTexture = l.game.assetManager.getTexture("powerups/shield.png");
		shieldImage = new Sprite(shieldTexture);
		
		// Image renders weird at it's natural size so adjusting by 1 pixel
		shieldImage.setSize(shieldImage.getWidth()+1, shieldImage.getHeight());
		
		
		shape = new PolygonShape();
		
		float vertices[] = {
				-38, -25,
				-1, 31,
				37, -30,
				19, -23,
				10, -36,
				-10, -36,
				-19, -25
		};
		
		shape.set(vertices);

		
		shieldShape = new PolygonShape();
		float shieldVertices[] = {
				-46, -30,
				-25, -49,
				12, -55,
				45, -29,
				55, 13,
				25, 51,
				-26, 53,
				-55, 16
		};
		
		shieldShape.set(shieldVertices);
		
	}
	
	public void hit(float damage) {
		
		if(hittable) {
			getLevel().addAction(GeneralHelper.shakeSprite(getSprite()));
			
			if(hasShield) {
				damage = 0;
			}
			
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
	}
	
	public void addHealth(float amount) {
		health += amount;
		
		if(health > MAX_HEALTH) {
			health = MAX_HEALTH;
		}
		
		level.healthBar.setHealth(health);
	}
	
	public void addArmor(float amount) {
		armor += amount;
		
		if(armor > MAX_ARMOR) {
			armor = MAX_ARMOR;
		}
		
		level.armorBar.setHealth(armor);
	}
	
	
	public void addCollidedObject(GameActor actor) {
		if(collidedObjects.indexOf(actor) == -1) {
			collidedObjects.add(actor);
			
			if(actor.getCollisionData().getActorType() == "Boss") {
				Boss tempBoss = (Boss) actor;
				tempBoss.setLastShipCollision();
				
				// PLAY EXPLOSION NOISE
				//Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion-2.wav"));
				//wavSound.play(ShooterGame.SFX_VOLUME);
				//GeneralHelper.disposeSound(wavSound, 2f);
				
				level.game.assetManager.playSound("sounds/explosion-2.wav", 2f);
				
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
		
		if(hasShield) {
			shieldImage.setPosition(sprite.getX()-((shieldImage.getWidth()/2)-(sprite.getWidth()/2)), sprite.getY()-((shieldImage.getHeight()/2)-(sprite.getHeight()/2)));
			shieldOpacity = 1-((Spawner.getSeconds() - shieldStartTime)/Powerup.SHIELD_TIME);
			
			if(shieldOpacity < .1) {
				shieldOpacity = .1f;
			}
			
			shieldImage.draw(batch, shieldOpacity);
		}
		
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
			
			if(upKeyPressed && getY() >= maxY) {
				level.barrier.setX(this.getX()+(this.getWidth()/2)-(level.barrier.getWidth()/2));
				level.barrier.setVisible(true);
			}
			else {
				level.barrier.setVisible(false);
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
		removeShield();
		
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
			maxY = this.getStage().getHeight() - (getHeight()*4);
			this.level.barrier.setY(maxY+(this.level.barrier.getHeight()/2));
			
			/*
			stage.addListener(new ClickListener() {
				
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					
					if(!level.screen.gamePaused && button == Input.Buttons.LEFT) {
						
						Bullet bullet = new Bullet(level, Ship.this.getX(), Ship.this.getY());
						
						bullet.setX(Ship.this.getX()+(Ship.this.getWidth()/2)-(bullet.getWidth()/2));
						bullet.setY(Ship.this.getY()+(Ship.this.getHeight()/2));

						level.addGameObject(bullet);
						
						
						createBody();				
					}
					
					
					return true;
				}
				
				
			});
			*/
			
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
		
		/*
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
		*/
	}
	
	public void releaseParticles() {
		level.game.assetManager.releaseParticle(gasParticleInterface, 0f);
		level.game.assetManager.releaseParticle(smokingParticleInterface, 0f);
		level.game.assetManager.releaseParticle(dangerParticleInterface, 0f);
	}
	
	public void explode() {
		// EXPLODE
		Asset<ParticleInterface> particleInterface = level.game.assetManager.getParticle("FireExplosion");
		FireExplosion explosion = (FireExplosion) particleInterface.asset;
		level.addGameObject(explosion);
		explosion.start(this.getX(), this.getY());
		level.game.assetManager.releaseParticle(particleInterface, FireExplosion.RELEASE_TIME);
	}
	
	@Override
	protected void createBodyBoundry() {
		
		bodyXOffset = (getParent().getStage().getWidth()/2)-(sprite.getWidth()/2);
		bodyYOffset = (getParent().getStage().getHeight()/2)-(sprite.getHeight()/2);
		
		// CREATE A NEW BODY
		
		if(bodyDef == null) {
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bodyDef.position.set(sprite.getX()-bodyXOffset, sprite.getY()-bodyYOffset);
		

		body = getWorld().createBody(bodyDef);
		
		if(!hasShield) {
			fixture = body.createFixture(shape, 0f);
		}
		else {
			fixture = body.createFixture(shieldShape, 0f);
		}
		
		fixture.setUserData(collisionData);
		body.resetMassData();
		//shape.dispose();
	}

	public void applyShield() {
		
		if(hasShield && shieldTimer != null) {
			shieldTimer.cancel();
		}
		

		hasShield = true;
		shieldStartTime = Spawner.getSeconds();
		// Remove Shield after certain amount of time
		shieldTimer = Timer.schedule(new Task() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				removeShield();
			}
			
		}, Powerup.SHIELD_TIME);
		
	}
	
	public void removeShield() {
		hasShield = false;
	}
	
	public boolean hasShield() {
		return hasShield;
	}
}
