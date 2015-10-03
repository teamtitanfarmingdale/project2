package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Ship extends Actor {

	Texture shipTexture = new Texture(Gdx.files.internal("ship.png"));
	Sprite shipSprite = new Sprite(shipTexture);
	
	boolean rightKeyPressed = false;
	boolean leftKeyPressed = false;
	
	float movementSpeed = .01f;
	float movementDistance = 10f;
	
	public Ship() {
		setBounds(shipSprite.getX(), 10, shipSprite.getWidth(), shipSprite.getHeight());
		setTouchable(Touchable.enabled);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		shipSprite.draw(batch);
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		MoveByAction mba = new MoveByAction();
		
		float minX = 0;
		float maxX = this.getStage().getWidth() - getWidth();
			
		if(rightKeyPressed && getX() < maxX) {
			mba.setAmount(movementDistance,  0f);
			
		}
		else if(leftKeyPressed && getX() > minX) {
			mba.setAmount(movementDistance*-1,  0f);
		}
		
		mba.setDuration(movementSpeed);
		addAction(mba);
		
	}
	
	@Override
	protected void positionChanged() {
		shipSprite.setPosition(getX(), getY());
		super.positionChanged();
	}
	
	
	@Override
	protected void setStage(Stage stage) {
		super.setStage(stage);

		// ADD BULLET SHOOTING LISTENER AFTER STAGE IS SET
		if(stage != null) {
			stage.addListener(new ClickListener() {
				
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					
					if(button == Input.Buttons.LEFT) {
						
						Bullet bullet = new Bullet(Ship.this.getX(), Ship.this.getY());
						
						bullet.setX(bullet.getX()+(Ship.this.getWidth()/2)-(bullet.getWidth()/2));
						bullet.setY(bullet.getY()+(Ship.this.getHeight()/2));
						
						Ship.this.getStage().addActor(bullet);
												
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
				
					
					return true;
				}
				
			});
			
			
			
		}
	}
}
