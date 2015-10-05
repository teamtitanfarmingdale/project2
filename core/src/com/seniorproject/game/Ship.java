package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Ship extends GameActor {

	boolean rightKeyPressed = false;
	boolean leftKeyPressed = false;
	
	float movementSpeed = .01f;
	float movementDistance = 10f;

	// SHIP MOVEMENT CONSTRAINTS
	float minX = 0;
	float maxX = 0;
		
	
	public Ship(World world) {
		super(world);
		
		setupSprite("ship.png");
		
		collisionData.setActorType("Ship");
		setBounds(getSprite().getX(), 10, getSprite().getWidth(), getSprite().getHeight());
		setTouchable(Touchable.enabled);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		getSprite().draw(batch);
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
		
		
	}
	
	@Override
	protected void positionChanged() {
		getSprite().setPosition(getX(), getY());
		createBody();
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
			
			stage.addListener(new ClickListener() {
				
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					
					if(button == Input.Buttons.LEFT) {
						
						Bullet bullet = new Bullet(getWorld(), Ship.this.getX(), Ship.this.getY());
						
						bullet.setX(Ship.this.getX()+(Ship.this.getWidth()/2)-(bullet.getWidth()/2));
						bullet.setY(Ship.this.getY()+(Ship.this.getHeight()/2));
						
						Ship.this.getStage().addActor(bullet);
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
				}
				
					createBody();
					return true;
				}
				
			});
			
			
			
		}
	}
		
	
}
