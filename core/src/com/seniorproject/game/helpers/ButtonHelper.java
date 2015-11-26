package com.seniorproject.game.helpers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.seniorproject.game.ShooterGame;

public class ButtonHelper {

	ImageButtonStyle buttonStyle;
	Texture buttonTexture;
	ImageButton button;
	
	Texture disabledTexture;
	
	ShooterGame game;
	
	public ButtonHelper(String imageFile, int width, int height, int x, int y, int xHover, int yHover, ShooterGame g) {
	
		game = g;
		buttonTexture = game.assetManager.getTexture(imageFile);
		TextureRegion bButtonTextureRegion = new TextureRegion(buttonTexture, x, y, width, height);
		TextureRegion buttonTextureRegionHover = new TextureRegion(buttonTexture, xHover, yHover, width, height);
		
		TextureRegionDrawable trdButtonDrawable = new TextureRegionDrawable(bButtonTextureRegion);
		TextureRegionDrawable trdButtonDrawableHover = new TextureRegionDrawable(buttonTextureRegionHover);
		
		
		buttonStyle = new ImageButtonStyle(trdButtonDrawable, trdButtonDrawableHover, trdButtonDrawable, trdButtonDrawable, trdButtonDrawableHover, trdButtonDrawable);
		buttonStyle.imageOver = trdButtonDrawableHover;
		buttonStyle.checkedOver = trdButtonDrawableHover;
		button = new ImageButton(buttonStyle);
	}
	
	public ImageButton getButton() {
		return button;
	}
	
	public void setDisabledTexture(String file) {		
		disabledTexture = game.assetManager.getTexture(file);
		Sprite disabledSprite = new Sprite(disabledTexture);
		SpriteDrawable sdDisabled = new SpriteDrawable(disabledSprite);
		
		buttonStyle.imageDisabled = sdDisabled;
		
	}
	
	public void dispose() {
		/*
		if(buttonTexture != null) {
			buttonTexture.dispose();
		}
		
		if(disabledTexture != null) {
			disabledTexture.dispose();
		}
		*/
	}
	
	
}
