package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.seniorproject.game.PlayerSave;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.LabelHelper;

public class Slot extends Group {

	protected PlayMenu menu;

	protected Image selectButtonImage;
	
	protected TextureRegion buttonNormalStateTexture;
	protected TextureRegion buttonHoverStateTexture;
	protected TextureRegion buttonSelectedStateTexture;
	
	protected boolean saveSelected = false;
	
	protected PlayerSave save;
	
	public Slot(PlayerSave save, PlayMenu menu) {

		this.menu = menu;
		this.save = save;

		Texture bgTexture = new Texture(Gdx.files.internal("menu/save-slot-bg.png"));
		
		buttonNormalStateTexture = new TextureRegion(bgTexture, 0, 0, 248, 88);
		buttonHoverStateTexture = new TextureRegion(bgTexture, 0, 88, 248, 88);
		buttonSelectedStateTexture = new TextureRegion(bgTexture, 0, 176, 248, 88);

		selectButtonImage = new Image(buttonNormalStateTexture);
		
		
		this.addActor(selectButtonImage);
		this.setSize(220, 90);
		
		
		// Button Listeners
		this.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!saveSelected) {
					selectButtonImage.setDrawable(new TextureRegionDrawable(buttonHoverStateTexture));
				}
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!saveSelected) {
					selectButtonImage.setDrawable(new TextureRegionDrawable(buttonNormalStateTexture));
				}
			}
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
				Slot.this.menu.selectSaveSlot(Slot.this);
				
				return true;
			}
			
		});
		
	}
	
	public void reset() {
		selectButtonImage.setDrawable(new TextureRegionDrawable(buttonNormalStateTexture));
		saveSelected = false;
	}
	
	public boolean select() {
		if(!saveSelected) {
			saveSelected = true;
			selectButtonImage.setDrawable(new TextureRegionDrawable(buttonSelectedStateTexture));
		}
		else {
			saveSelected = false;
			selectButtonImage.setDrawable(new TextureRegionDrawable(buttonNormalStateTexture));
		}
		
		return saveSelected;
	}
	
	public PlayerSave getPlayerSave() {
		return save;
	}
	
	
	public void dispose() {
		buttonNormalStateTexture.getTexture().dispose();
	}
	
	public Image getSelectButtonImage() {
		return selectButtonImage;
	}
}
