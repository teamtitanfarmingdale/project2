package com.seniorproject.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.levels.Level;

public class PowerupContainerOLD extends Group {

	Level level;
	Image bigCircle;
	Image smallCircle;
	boolean rotateCircles = true;
	
	public PowerupContainerOLD(Level l) {
		super();
		level = l;
		Texture circleBG = level.game.assetManager.getTexture("powerups/spiralbg.png");
		TextureRegion smallCircleRegion = new TextureRegion(circleBG);
		TextureRegion bigCircleRegion = new TextureRegion(circleBG);
		
		smallCircleRegion.setRegion(0, 0, 83, 89);
		bigCircleRegion.setRegion(0, 101, 105, 109);
		
		bigCircle = new Image(bigCircleRegion);
		smallCircle = new Image(smallCircleRegion);
		
		bigCircle.setOrigin(Align.center);
		smallCircle.setOrigin(Align.center);
		
		bigCircle.setPosition(level.getWidth()/2, level.getHeight()/2);
		
		smallCircle.setPosition(bigCircle.getX()+(bigCircle.getWidth()/2)-(smallCircle.getWidth()/2), bigCircle.getY()+(bigCircle.getHeight()/2)-(smallCircle.getHeight()/2));
		
		
		//bigCircle.setDebug(true);
		//smallCircle.setDebug(true);
		
		
		this.addActor(bigCircle);
		this.addActor(smallCircle);
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
			
		if(rotateCircles) {
			rotateCircles = false;
			bigCircle.rotateBy(10);
			smallCircle.rotateBy(-8);
			
			Timer.schedule(new Task() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					rotateCircles = true;
				}
				
			}, .05f);
		}
		
	}
	
}
