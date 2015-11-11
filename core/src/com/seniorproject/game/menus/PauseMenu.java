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
	
	SliderHelper sfxSliderHelper;
	SliderHelper musicSliderHelper;
	Sprite musicTextSprite;
	Sprite sfxTextSprite;

	SpriteBatch batch;
	Stage stage;
	
	public PauseMenu(final GameScreen s) {

		int topButtonOffset = 40;
		int buttonOffset = 20;

		stage = new Stage();
		
		// BUTTONS
		resumeButtonHelper = new ButtonHelper("menu/resume-button.png", 204, 63, 0, 0, 0, 63);
		ImageButton resumeButton = resumeButtonHelper.getButton();
		resumeButton.setPosition((stage.getWidth()/2)-(resumeButton.getWidth()/2), (stage.getHeight()/2)+topButtonOffset);
		
		quitButtonHelper = new ButtonHelper("menu/quit-button.png", 204, 63, 0, 0, 0, 63);
		ImageButton quitButton = quitButtonHelper.getButton();
		quitButton.setPosition((stage.getWidth()/2)-(quitButton.getWidth()/2), resumeButton.getY()-quitButton.getHeight()-buttonOffset);
		
		// SLIDERS
		Texture sfxTextTexture = new Texture(Gdx.files.internal("menu/sfx-text.png"));
		sfxTextSprite = new Sprite(sfxTextTexture);
		
		Texture musicTextTexture = new Texture(Gdx.files.internal("menu/music-text.png"));
		musicTextSprite = new Sprite(musicTextTexture);
		
		sfxTextSprite.setPosition((stage.getWidth()/2)-(sfxTextSprite.getWidth()/2), quitButton.getY()-sfxTextSprite.getHeight()-buttonOffset);
		
		sfxSliderHelper = new SliderHelper("menu/sliderbar.png", "menu/slider-mark.png");
		musicSliderHelper = new SliderHelper("menu/sliderbar.png", "menu/slider-mark.png");
		
		final Slider sfxSlider = sfxSliderHelper.getSlider();
		sfxSlider.setValue(ShooterGame.SFX_VOLUME);
		
		final Slider musicSlider = musicSliderHelper.getSlider();
		musicSlider.setValue(ShooterGame.MUSIC_VOLUME);
		
		sfxSlider.setPosition((stage.getWidth()/2)-(sfxSlider.getWidth()/2), sfxTextSprite.getY()-sfxSlider.getHeight());

		musicTextSprite.setPosition((stage.getWidth()/2)-(musicTextSprite.getWidth()/2), sfxSlider.getY()-musicTextSprite.getHeight()-buttonOffset);
		musicSlider.setPosition((stage.getWidth()/2)-(musicSlider.getWidth()/2), musicTextSprite.getY()-musicSlider.getHeight());
		
		
		
		// FADED BACKGROUND
		fadedBGTexture = new Texture(Gdx.files.internal("faded-black-bg.png"));
		fadedBGTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		fadedBG = new Sprite(fadedBGTexture);
		fadedBG.setSize(ShooterGame.GAME_WIDTH, ShooterGame.GAME_HEIGHT);

		// PAUSE MENU BORDER
		menuTexture = new Texture(Gdx.files.internal("menu/pausemenu.png"));
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
				ShooterGame.MUSIC_VOLUME = musicSlider.getValue();
				s.getLevel().background.setBGVolume(ShooterGame.MUSIC_VOLUME);
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
		stage.addActor(quitButton);
		stage.addActor(sfxSlider);
		stage.addActor(musicSlider);
	}
	
	
	public void draw() {
		// TODO Auto-generated method stub

		this.stage.act(Gdx.graphics.getDeltaTime());

		batch.begin();
		fadedBG.draw(batch);
		menuBorder.draw(batch);
		sfxTextSprite.draw(batch);
		musicTextSprite.draw(batch);
		batch.end();

		this.stage.draw();
		
	}

	public void dispose() {
		resumeButtonHelper.dispose();
		quitButtonHelper.dispose();
		menuTexture.dispose();
		fadedBGTexture.dispose();
		sfxTextSprite.getTexture().dispose();
		musicSliderHelper.dispose();
		sfxSliderHelper.dispose();
	}
	
	public Stage getStage() {
		return stage;
	}
	
}
