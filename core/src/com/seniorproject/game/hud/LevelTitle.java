package com.seniorproject.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.LabelHelper;

public class LevelTitle extends BaseHUD {
	
	Texture wingTexture;
	Sprite wingSprite;
	LabelHelper label;
	
	boolean positionSet = false;
	
	String title;
	
	float wingX;
	float wingY;
	
	public LevelTitle(String title, ShooterGame g) {
		super(g);
		
		wingTexture = game.assetManager.getTexture("hud/leveltitle/wing2.png");
		wingSprite = new Sprite(wingTexture);
		
		label = new LabelHelper(title, 20, Color.BLACK, g);


	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		
		if(!positionSet && getParent().getStage() != null && positionXOffsetWidth != 0 && positionYOffsetHeight != 0) {
			
			positionSet = true;
			
			parentWidth = getParent().getStage().getWidth();
			parentHeight = getParent().getStage().getHeight();
			
			/*
			 * bottomLeftWingX = halfParentWidth
					- (bottomLeftWingSprite.getWidth() * 1.1f - (positionXOffsetWidth / 50.5f));
					
			bottomLeftWingY = topLeftWingY - (bottomLeftWingSprite.getHeight() * 1.30f)
					+ (positionYOffsetHeight / 175.7f);
			 */
			
			
			wingX = parentWidth/2 + (wingSprite.getWidth()/10.8f);
			wingY = parentHeight - wingSprite.getHeight() - (positionYOffsetHeight/2.21f);

			wingSprite.setPosition(wingX, wingY);
			
			label.getLabel().setPosition(wingX+(wingSprite.getWidth()/1.5f), wingY-(label.getLabel().getHeight()/10f));
			
		}
		
		
		wingSprite.draw(batch);
		
	}
	
	public Label getLabel() {
		return label.getLabel();
	}
	
	
}
