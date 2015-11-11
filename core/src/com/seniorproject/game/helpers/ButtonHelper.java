package com.seniorproject.game.helpers;

import java.awt.Cursor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonHelper {

	Texture buttonTexture;
	ImageButton button;
	
	public ButtonHelper(String imageFile, int width, int height, int x, int y, int xHover, int yHover) {
	
		
		buttonTexture = new Texture(Gdx.files.internal(imageFile));
		TextureRegion bButtonTextureRegion = new TextureRegion(buttonTexture, x, y, width, height);
		TextureRegion buttonTextureRegionHover = new TextureRegion(buttonTexture, xHover, yHover, width, height);
		
		TextureRegionDrawable trdButtonDrawable = new TextureRegionDrawable(bButtonTextureRegion);
		TextureRegionDrawable trdButtonDrawableHover = new TextureRegionDrawable(buttonTextureRegionHover);
		
		
		ImageButtonStyle buttonStyle = new ImageButtonStyle(trdButtonDrawable, trdButtonDrawableHover, trdButtonDrawable, trdButtonDrawable, trdButtonDrawableHover, trdButtonDrawable);
		buttonStyle.imageOver = trdButtonDrawableHover;
		buttonStyle.checkedOver = trdButtonDrawableHover;
		button = new ImageButton(buttonStyle);
	}
	
	public ImageButton getButton() {
		return button;
	}
	
	public void dispose() {
		
		if(buttonTexture != null) {
			buttonTexture.dispose();
		}
		
	}
	
	
}
