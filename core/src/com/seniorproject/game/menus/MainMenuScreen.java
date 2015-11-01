package com.seniorproject.game.menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.seniorproject.game.ButtonHelper;
import com.seniorproject.game.LevelBackground;
import com.seniorproject.game.ShooterGame;

public class MainMenuScreen implements Screen {

	ShooterGame game;
	Stage stage;
	
	SpriteBatch batch;
	Sprite menuBorder;
	
	LevelBackground levelBG;
	ButtonHelper buttonHelper;
	
	public MainMenuScreen(ShooterGame g) {
		
		game = g;
		create();
		
	}
	
	
	public void create() {
		
		batch = new SpriteBatch();
		stage = new Stage();

		Texture menuBorderTexture = new Texture(Gdx.files.internal("menu/menuborder.png"));
		
		levelBG = new LevelBackground(.00005f);
		
		menuBorder = new Sprite(menuBorderTexture);
		menuBorder.setPosition((stage.getWidth()/2)-(menuBorder.getWidth()/2), (stage.getHeight()/2)-(menuBorder.getHeight()/2)-50);
		
		
		buttonHelper = new ButtonHelper("menu/newgame-button.png", 204, 63, 0, 0, 0, 63);
		
		ImageButton button = buttonHelper.getButton();
		
		button.setPosition((stage.getWidth()/2)-(button.getWidth()/2), (stage.getHeight()/2)-(button.getHeight()/2));
	
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.switchScreen(ShooterGame.GAME);
			}
		});
		
		stage.addActor(levelBG);
		stage.addActor(button);
		
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
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		batch.begin();
		menuBorder.draw(batch);
		batch.end();
		
		
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
		levelBG.dispose();
		menuBorder.getTexture().dispose();
		buttonHelper.dispose();
	}

	
	
}
