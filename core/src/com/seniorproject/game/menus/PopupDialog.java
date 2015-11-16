package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;

public class PopupDialog {
	
	protected boolean hidden = true;

	protected Stage stage;
	protected Stage parentStage;
	
	protected Sprite dialogSprite;
	protected ButtonHelper cancelButtonHelper;
	protected ImageButton cancelButton;
	
	protected Texture fadedBGTexture;
	protected Sprite fadedBG;
	
	public PopupDialog(Stage parentStage) {
		
		this.parentStage = parentStage;
		
		stage = new Stage();
		
		// FADED BACKGROUND
		fadedBGTexture = new Texture(Gdx.files.internal("faded-black-bg.png"));
		fadedBGTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		fadedBG = new Sprite(fadedBGTexture);
		fadedBG.setSize(ShooterGame.GAME_WIDTH, ShooterGame.GAME_HEIGHT);
		
		
		// Dialog
		Texture dialogTexture = new Texture(Gdx.files.internal("menu/save-score-popup/bg.png"));
		dialogSprite = new Sprite(dialogTexture);
		
		dialogSprite.setSize(536, 286);
		dialogSprite.setPosition((stage.getWidth()/2)-(dialogSprite.getWidth()/2), (stage.getHeight()/2)-(dialogSprite.getHeight()/2));
		
		
		// Cancel Button
		cancelButtonHelper = new ButtonHelper("menu/save-score-popup/cancel-button.png", 71, 71, 0, 0, 0, 71);
		cancelButton = cancelButtonHelper.getButton();
		
		cancelButton.setPosition((dialogSprite.getX()+dialogSprite.getWidth()-(cancelButton.getWidth()/2)-15), dialogSprite.getY()+dialogSprite.getHeight()-cancelButton.getHeight()+15);
		
		
		// Button Listeners
		cancelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
			}
		});
		

		stage.addActor(cancelButton);

	}
	
	public void draw(SpriteBatch batch) {

		if(!hidden) {
			fadedBG.draw(batch);
			dialogSprite.draw(batch);
		}
		
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void hide() {
		hidden = true;
		Gdx.input.setInputProcessor(parentStage);
		
	}
	
	public void show() {
		hidden = false;
		Gdx.input.setInputProcessor(stage);
	}
	
	public boolean isVisible() {
		return !hidden;
	}
	
	
	public void dispose() {
		fadedBGTexture.dispose();
		cancelButtonHelper.dispose();
		stage.dispose();
	}
}
