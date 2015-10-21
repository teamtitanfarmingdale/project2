package com.seniorproject.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.seniorproject.game.LabelHelper;

public class LevelTitle extends BaseHUD {
	
	Texture wingTexture;
	Sprite wingSprite;
	LabelHelper label;
	
	boolean positionSet = false;
	
	String title;
	
	float wingX;
	float wingY;
	
	public LevelTitle(String title) {
		
		
		wingTexture = new Texture(Gdx.files.internal("hud/leveltitle/wing.png"));
		wingSprite = new Sprite(wingTexture);
		
		label = new LabelHelper(title, 20, Color.BLACK);


	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		
		if(!positionSet && getParent().getStage() != null && positionXOffsetWidth != 0 && positionYOffsetHeight != 0) {
			
			positionSet = true;
			
			parentWidth = getParent().getStage().getWidth();
			parentHeight = getParent().getStage().getHeight();
			
			wingX = parentWidth/2 - (positionXOffsetWidth/11.5f);
			wingY = parentHeight - wingSprite.getHeight() - (positionYOffsetHeight/2.52f);

			wingSprite.setPosition(wingX, wingY);
			
			label.getLabel().setPosition(wingX+(wingSprite.getWidth()/1.5f), wingY+(wingSprite.getHeight()/12));
			
		}
		
		
		wingSprite.draw(batch);
		
	}
	
	public Label getLabel() {
		return label.getLabel();
	}
	
	
}
