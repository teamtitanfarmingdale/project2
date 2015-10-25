package com.seniorproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
	
	float health = 100;
	float armor = 100;
	
	ArrayList<GameActor> collidedObjects;
	
	
	public Ship(World world) {
		super(world);
		
		setupSprite("ship.png");
		
		collisionData.setActorType("Ship");
		setBounds(getSprite().getX(), 10, getSprite().getWidth(), getSprite().getHeight());
		setTouchable(Touchable.enabled);
		
		collidedObjects = new ArrayList<GameActor>();
		
	}
	
	public void hit(float damage) {
		
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
		
		if(health == 0 && armor == 0) {
			level.healthBar.lifeManager.removeLife();
			armor = MAX_ARMOR;
			health = MAX_HEALTH;
		}
		
		
		level.armorBar.setHealth(armor);
		level.healthBar.setHealth(health);
	}
	
	public void addCollidedObject(GameActor actor) {
		if(collidedObjects.indexOf(actor) == -1) {
			collidedObjects.add(actor);
		}
	}
	
	public boolean hasCollidedWith(GameActor actor) {
		boolean returnVal = (collidedObjects.indexOf(actor) != -1);
		return returnVal;
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		getSprite().draw(batch);
		createBody();
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);

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
		
		System.out.println(getY());
		
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
					
					if(button == Input.Buttons.LEFT) {
						
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
							System.out.println("up pressed");
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
						System.out.println("up unpressed");
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
		
	
}
