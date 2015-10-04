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

	Texture shipTexture = new Texture(Gdx.files.internal("ship.png"));
	Sprite shipSprite = new Sprite(shipTexture);
	
	boolean rightKeyPressed = false;
	boolean leftKeyPressed = false;
	
	float movementSpeed = .01f;
	float movementDistance = 10f;

	// SHIP MOVEMENT CONSTRAINTS
	float minX = 0;
	float maxX = 0;
		
	
	public Ship(World world) {
		super(world);
		collisionData.setActorType("Ship");
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

		if(rightKeyPressed && getX() < maxX) {
			MoveByAction mba = new MoveByAction();
			float bodyXOffset = (getParent().getStage().getWidth()/2)-(shipSprite.getWidth()/2);
			float bodyYOffset = (getParent().getStage().getHeight()/2)-(shipSprite.getHeight()/2);
			
			mba.setAmount(movementDistance,  0f);
			mba.setDuration(movementSpeed);
			//createBody();
			addAction(mba);
			
		}
		else if(leftKeyPressed && getX() > minX) {
			MoveByAction mba = new MoveByAction();
			float bodyXOffset = (getParent().getStage().getWidth()/2)-(shipSprite.getWidth()/2);
			float bodyYOffset = (getParent().getStage().getHeight()/2)-(shipSprite.getHeight()/2);
			
			
			mba.setAmount(movementDistance*-1,  0f);
			mba.setDuration(movementSpeed);
			//createBody();
			addAction(mba);
		}
		
		
	}
	
	@Override
	protected void positionChanged() {
		shipSprite.setPosition(getX(), getY());
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
	
	public void createBody() {

		if(getStage() != null) {
			float bodyXOffset = (getParent().getStage().getWidth()/2)-(shipSprite.getWidth()/2);
			float bodyYOffset = (getParent().getStage().getHeight()/2)-(shipSprite.getHeight()/2);
			
			// DESTROY THE CURRENT BODY IF THERE IS ONE
			if(body != null) {
				body.destroyFixture(fixture);
			}
			
			// CREATE A NEW BODY
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			bodyDef.position.set(shipSprite.getX()-bodyXOffset, shipSprite.getY()-bodyYOffset);
			
			shape = new PolygonShape();
			shape.setAsBox(shipSprite.getWidth()/2, shipSprite.getHeight()/2);
			
			
			body = getWorld().createBody(bodyDef);
			fixture = body.createFixture(shape, 0f);
			fixture.setUserData(collisionData);
			body.resetMassData();
			shape.dispose();
		}
	}
	
	
}
