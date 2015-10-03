package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Bullet extends Actor {

	Texture bulletTexture = new Texture(Gdx.files.internal("bullet.png"));
	Sprite bulletSprite = new Sprite(bulletTexture);
	
	float bulletSpeed = .01f;
	float movementDistance = 10f;
	
	
	public Bullet(float x, float y) {
		
		setBounds(x, y, bulletSprite.getWidth(), bulletSprite.getHeight());
		
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		bulletSprite.draw(batch);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		MoveByAction mba = new MoveByAction();
		
		mba.setAmount(0,  movementDistance);
		mba.setDuration(bulletSpeed);
		
		addAction(mba);
		
	}
	
	
	@Override 
	protected void positionChanged() {
		bulletSprite.setPosition(getX(), getY());
		super.positionChanged();
		
		// REMOVE THE BULLET ONCE IT IS OFF THE SCREEN
		if(getStage() != null) {
			if(getY() > getStage().getHeight()) {
				this.remove();
			}
		}
		
		
	}
	
}
