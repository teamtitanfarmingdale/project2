package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.SliderHelper;

public class MainMenuScreen extends BaseMenu implements Screen {

	Sprite logo;
	Sprite musicTextSprite;
	Sprite sfxTextSprite;

	ButtonHelper newGameButtonHelper;
	ButtonHelper loadGameButtonHelper;
	ButtonHelper quitButtonHelper;
	
	SliderHelper sfxSliderHelper;
	SliderHelper musicSliderHelper;
	
	public MainMenuScreen(ShooterGame g) {
		super(g);
		create();
	}
	
	
	public void create() {
		
		int borderOffset = 80;
		int buttonOffset = 20;
		
		init("menu/mainmenu.png", borderOffset);
		
		// RESET PLAYER SCORE
		ShooterGame.PLAYER_SCORE = 0;
		
		// LOGO
		Texture logoTexture = new Texture(Gdx.files.internal("logo-small.png"));
		logo = new Sprite(logoTexture);
		logo.setPosition((stage.getWidth()/2)-(logo.getWidth()/2), stage.getHeight()-logo.getHeight());
		
		
		// SLIDER TITLES
		Texture sfxTextTexture = new Texture(Gdx.files.internal("menu/sfx-text.png"));
		sfxTextSprite = new Sprite(sfxTextTexture);
		
		Texture musicTextTexture = new Texture(Gdx.files.internal("menu/music-text.png"));
		musicTextSprite = new Sprite(musicTextTexture);
		
		// BUTTONS
		newGameButtonHelper = new ButtonHelper("menu/start-button.png", 204, 64, 0, 0, 0, 63);
		ImageButton newGameButton = newGameButtonHelper.getButton();
		
		loadGameButtonHelper = new ButtonHelper("menu/load-button.png", 204, 63, 0, 0, 0, 63);
		ImageButton loadButton = loadGameButtonHelper.getButton();
		
		quitButtonHelper = new ButtonHelper("menu/quit-button.png", 204, 63, 0, 0, 0, 63);
		ImageButton quitButton = quitButtonHelper.getButton();
		
		
		newGameButton.setPosition((stage.getWidth()/2)-(newGameButton.getWidth()/2), (stage.getHeight()/2)-(newGameButton.getHeight()/2)-buttonOffset);
		//loadButton.setPosition((stage.getWidth()/2)-(loadButton.getWidth()/2), newGameButton.getY()-loadButton.getHeight()-buttonOffset);
		quitButton.setPosition((stage.getWidth()/2)-(quitButton.getWidth()/2), newGameButton.getY()-quitButton.getHeight()-buttonOffset);
		

		// SLIDERS
		
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
		
		// BUTTON EVENTS
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.switchScreen(ShooterGame.PLAY_MENU);
			}
		});
		
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		
		// SLIDER EVENTS
		musicSlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShooterGame.MUSIC_VOLUME = musicSlider.getValue();
				levelBG.setBGVolume(ShooterGame.MUSIC_VOLUME);
			}
			
		});
		
		sfxSlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				ShooterGame.SFX_VOLUME = sfxSlider.getValue();
			}
			
		});

		
		// ADD EVERYTHING TO THE STAGE
		stage.addActor(newGameButton);
		//stage.addActor(loadButton);
		stage.addActor(quitButton);
		stage.addActor(sfxSlider);
		stage.addActor(musicSlider);
		
		
		Gdx.input.setInputProcessor(stage);
		
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		
		this.batch.begin();
		this.menuBorder.draw(batch);
		logo.draw(batch);
		sfxTextSprite.draw(batch);
		musicTextSprite.draw(batch);
		this.batch.end();
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		newGameButtonHelper.dispose();
		quitButtonHelper.dispose();
		sfxSliderHelper.dispose();
		musicSliderHelper.dispose();
		sfxTextSprite.getTexture().dispose();
		musicTextSprite.getTexture().dispose();
		logo.getTexture().dispose();
	}

	
	
}
