package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.GameScreen;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.SliderHelper;

public class PauseMenu {

	Texture menuTexture;
	Sprite menuBorder;
	
	Texture fadedBGTexture;
	Sprite fadedBG;
	
	ButtonHelper resumeButtonHelper;
	ButtonHelper quitButtonHelper;
	ButtonHelper controlsButtonHelper;
	
	SliderHelper sfxSliderHelper;
	SliderHelper musicSliderHelper;
	Sprite musicTextSprite;
	Sprite sfxTextSprite;

	SpriteBatch batch;
	Stage stage;
	
	ControlsDialog controlsDialog;
	
	
	public PauseMenu(final GameScreen s) {

		int topButtonOffset = 40;
		int buttonOffset = 5;

		stage = new Stage();
		
		controlsDialog = new ControlsDialog(stage, s.game);
		
		// BUTTONS
		resumeButtonHelper = new ButtonHelper("menu/resume-button.png", 204, 63, 0, 0, 0, 63, s.game);
		ImageButton resumeButton = resumeButtonHelper.getButton();
		resumeButton.setPosition((stage.getWidth()/2)-(resumeButton.getWidth()/2), (stage.getHeight()/2)+topButtonOffset);
		
		controlsButtonHelper = new ButtonHelper("menu/controls-button.png", 204, 63, 0, 0, 0, 63, s.game); 
		ImageButton controlsButton = controlsButtonHelper.getButton();
		controlsButton.setPosition((stage.getWidth()/2)-(controlsButton.getWidth()/2), resumeButton.getY()-controlsButton.getHeight()-buttonOffset);
		
		quitButtonHelper = new ButtonHelper("menu/quit-button.png", 204, 63, 0, 0, 0, 63, s.game);
		ImageButton quitButton = quitButtonHelper.getButton();
		quitButton.setPosition((stage.getWidth()/2)-(quitButton.getWidth()/2), controlsButton.getY()-quitButton.getHeight()-buttonOffset);
		
		
		
		// SLIDERS
		Texture sfxTextTexture = s.game.assetManager.getTexture("menu/sfx-text.png");
		sfxTextSprite = new Sprite(sfxTextTexture);
		
		Texture musicTextTexture = s.game.assetManager.getTexture("menu/music-text.png");
		musicTextSprite = new Sprite(musicTextTexture);
		
		sfxTextSprite.setPosition((stage.getWidth()/2)-(sfxTextSprite.getWidth()/2), quitButton.getY()-sfxTextSprite.getHeight()-(buttonOffset*3));
		
		sfxSliderHelper = new SliderHelper("menu/sliderbar.png", "menu/slider-mark.png", s.game);
		musicSliderHelper = new SliderHelper("menu/sliderbar.png", "menu/slider-mark.png", s.game);
		
		final Slider sfxSlider = sfxSliderHelper.getSlider();
		sfxSlider.setValue(ShooterGame.SFX_VOLUME);
		
		final Slider musicSlider = musicSliderHelper.getSlider();
		musicSlider.setValue(ShooterGame.MUSIC_VOLUME);
		
		sfxSlider.setPosition((stage.getWidth()/2)-(sfxSlider.getWidth()/2), sfxTextSprite.getY()-sfxSlider.getHeight());

		musicTextSprite.setPosition((stage.getWidth()/2)-(musicTextSprite.getWidth()/2), sfxSlider.getY()-musicTextSprite.getHeight()-buttonOffset);
		musicSlider.setPosition((stage.getWidth()/2)-(musicSlider.getWidth()/2), musicTextSprite.getY()-musicSlider.getHeight());
		
		
		
		// FADED BACKGROUND
		fadedBGTexture = s.game.assetManager.getTexture("faded-black-bg.png");
		fadedBGTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		fadedBG = new Sprite(fadedBGTexture);
		fadedBG.setSize(ShooterGame.GAME_WIDTH, ShooterGame.GAME_HEIGHT);

		// PAUSE MENU BORDER
		menuTexture = s.game.assetManager.getTexture("menu/pausemenu.png");
		menuBorder = new Sprite(menuTexture);
		
		menuBorder.setPosition((stage.getWidth()/2)-(menuBorder.getWidth()/2), (stage.getHeight()/2)-(menuBorder.getHeight()/2));
		
		
		batch = new SpriteBatch();
		
		// BUTTON EVENTS
		resumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				s.resume();
			}
		});
		
		controlsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlsDialog.show();
			}
		});
		
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				s.game.switchScreen(ShooterGame.MAIN_MENU);
			}
		});
		
		// SLIDER EVENTS
		musicSlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShooterGame.setBGMusicVolume(musicSlider.getValue());
			}
			
		});
		
		sfxSlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShooterGame.SFX_VOLUME = sfxSlider.getValue();
			}
			
		});
		
		stage.addActor(resumeButton);
		stage.addActor(controlsButton);
		stage.addActor(quitButton);
		stage.addActor(sfxSlider);
		stage.addActor(musicSlider);
		
	}
	
	
	public void draw() {
		// TODO Auto-generated method stub

		this.stage.act(Gdx.graphics.getDeltaTime());

		batch.begin();
		
		if(!controlsDialog.isVisible()) {
			fadedBG.draw(batch);
		}
		
		menuBorder.draw(batch);
		sfxTextSprite.draw(batch);
		musicTextSprite.draw(batch);
		controlsDialog.draw(batch);
		batch.end();
		

		if(controlsDialog.isVisible()) {
			controlsDialog.getStage().act(Gdx.graphics.getDeltaTime());
			controlsDialog.getStage().draw();
		}
		else {
			this.stage.draw();
		}
		
	}

	public void dispose() {
		resumeButtonHelper.dispose();
		quitButtonHelper.dispose();
		//menuTexture.dispose();
		//fadedBGTexture.dispose();
		//sfxTextSprite.getTexture().dispose();
		musicSliderHelper.dispose();
		sfxSliderHelper.dispose();
		stage.dispose();
	}
	
	public Stage getStage() {
		return stage;
	}
	
}
