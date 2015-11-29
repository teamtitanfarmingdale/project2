package com.seniorproject.game.enemies;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.Asset;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.Spawner;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.BaseExplosion;
import com.seniorproject.game.particles.ParticleInterface;

public class BaseEnemy extends GameActor {

	protected int health = 100;
	protected int startingHealth = 100;
	protected int randomX;
	protected Double randomNumber; // Used for random position
	protected boolean reposition = false;
	
	protected int collisionDamage = 25;
	protected int hitAwardPoints = 5;
	protected int killAwardPoints = 25;
	
	
	protected float movementDistance = 2f;
	protected float movementXDistance = 2f;
	protected float movementYDistance = 2f;
	
	protected float movementSpeed = .01f;
	
	protected float lastMoveTime = -1;
	
	protected float hoverOffset = 0;
	protected boolean hoverPhase = true;
	
	protected float hoverSpeed = 5;
	
	
	protected float lastShootTime = -1;
	protected float shootInterval = 1;
	
	protected float diveRadius = 100;
	
	protected boolean customMovement = false;
	
	protected Asset<ParticleInterface> particleInterface;
	protected BaseExplosion explosion;
	
	public boolean hitByEMP = false;
	
	public BaseEnemy(Level l, String spriteFile) {
		super(l);
		
		setupSprite(spriteFile);
		
		
		// Randomize movement speed
		movementDistance = (float) (Math.random() * 2f)+1f;
		
		movementXDistance = (float) (Math.random() * 2f)+1f;
		if(Math.random()*100 > 50) {
			movementXDistance *= -1;
		}
		
		
		movementSpeed = (float) (Math.random() * .01f)+.01f;
		
		hoverOffset = (float) (Math.random()*50f)+250;
		hoverSpeed = (float) (Math.random()*3f)+2f;
		

		shootInterval = (float) (Math.random()*.5f)+1f;
		
	}
	
	
	public int getHealth() {
		return health;
	}
	
	public void act(float delta) {
		super.act(delta);
		
		if(!customMovement) {
			if(!level.screen.gamePaused) {
				
				if(health <= 0) {
					level.score.addToScore(killAwardPoints);
					level.ship.totalKills++;
					setDead(true);
				}
				else if(reposition) {
					
					movementXDistance = movementDistance;
					if(getSprite().getX() > (level.getWidth()/2)) {
						movementXDistance = Math.abs(movementDistance)*-1;
					}
					
					MoveByAction mba = new MoveByAction();
					mba.setAmount(movementXDistance, 0f);
					mba.setDuration(movementSpeed);
					addAction(mba);
					reposition = false;
					
					
				}
				else {
					
					movementYDistance = movementDistance;
					
					if(hoverPhase && getX() >= level.getShip().getX()-diveRadius && getX() <= level.getShip().getX()+diveRadius) {
						hoverPhase = false;
						lastMoveTime = -1;
					}
					
					
					
					
					if(getY() > (level.getHeight()-hoverOffset) || !hoverPhase) {
						// Enemy is moving down the screen
						if(!hoverPhase) {
							movementYDistance *= 3;
						
							// Shoot while moving downwards
							if(lastShootTime == -1) {
								lastShootTime = Spawner.getSeconds();
							}
							
							if(Spawner.getSeconds() - lastShootTime > shootInterval) {
								shoot();
								lastShootTime = Spawner.getSeconds();
							}
						
						}
						
						
						MoveByAction mba = new MoveByAction();
						mba.setAmount(0f, (movementYDistance*-1));
						mba.setDuration(movementSpeed);
						
						addAction(mba);
						addAction(Actions.moveTo(level.getShip().getX(), getSprite().getY(), hoverSpeed));
						
						
					}
					else {
						// Enemy is in hover mode following the player
						if(lastMoveTime == -1) {
							lastMoveTime = Spawner.getSeconds();
						}
						
						
						if(lastShootTime == -1) {
							lastShootTime = Spawner.getSeconds();
						}
						
						if(Spawner.getSeconds() - lastShootTime > shootInterval) {
							shoot();
							lastShootTime = Spawner.getSeconds();
						}
						
						
						addAction(Actions.moveTo(level.getShip().getX(), getSprite().getY(), hoverSpeed));
						
					}
					
					
				}
				
				if(getY()+getHeight() < 0 && movementDistance > 0) {
					// Move enemy up
					
					movementDistance *= -1;
					//System.out.println("go up");
					
				}
				else if(getY()+getHeight() > level.getHeight()-hoverOffset && movementDistance < 0) {
					movementDistance *= -1;
					//System.out.println("go down");
					
					if(lastMoveTime == -1 && !hoverPhase) {
						lastMoveTime = Spawner.getSeconds();
						hoverPhase = true;
					}
					
				}
				
		
				if(hoverPhase && lastMoveTime != -1 && Spawner.getSeconds()-lastMoveTime > 5) {
					lastMoveTime = -1;
					hoverPhase = false;
				}
			
			
			}
		}
	}
	
	public void draw(Batch batch, float alpha) {
		getSprite().draw(batch);
	}
	
	
	@Override
	public void setStage(Stage stage) {
		super.setStage(stage);
		
		if(stage != null) {
			
			randomNumber = (Math.random()*(stage.getWidth()-getSprite().getWidth()));
			randomX = randomNumber.intValue();
			
			// Spawns the enemy at a random X location at the top of the screen
			setBounds(randomX, (stage.getHeight()-(getSprite().getHeight()-100)), getSprite().getWidth(), getSprite().getHeight());

		}
		
	}
	
	@Override
	protected void positionChanged() {
		getSprite().setPosition(getX(), getY());
		createBody();
		super.positionChanged();
	}

	public void lowerHealth(int damage) {
		health -= damage;
		level.score.addToScore(hitAwardPoints);
	}
	
	public void reposition() {
		reposition = true;
	}
	
	public void setCollisionDamage(int damage) {
		collisionDamage = damage;
	}
	
	public int getCollisionDamage() {
		return collisionDamage;
	}
	
	public int getHitAwardPoints() {
		return hitAwardPoints;
	}
	
	public int getKillAwardPoints() {
		return killAwardPoints;
	}
	
	public void shoot() {
		
		
		EnemyBullet bullet = new EnemyBullet(level, this.getX(), this.getY());
		
		bullet.setX(this.getX()+(this.getWidth()/2)-(bullet.getWidth()/2));
		bullet.setY(this.getY()+(this.getHeight()/2));

		level.addGameObject(bullet);
		
	}

	@Override
	public void setDead(boolean dead) {

		if(!this.dead && dead) {
			
			explode();

			// PLAY EXPLOSION SOUND
			//final Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion-2.wav"));
			//wavSound.play(ShooterGame.SFX_VOLUME);
			//GeneralHelper.disposeSound(wavSound, 2f);
			level.game.assetManager.playSound("sounds/explosion-2.wav", 2f);
		}
		
		this.dead = dead;
		
	}
	
	public void silentDeath(boolean dead) {
		super.setDead(dead);
	}
	
	public void reset() {
		health = startingHealth;
		dead = false;
	}
	
	public void explode() {
		if(explosion != null) {
			level.addGameObject(explosion);
			explosion.start(this.getX(), this.getY());
		}
	}
}
