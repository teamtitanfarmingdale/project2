package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.seniorproject.game.BaseScreen;
import com.seniorproject.game.LevelBackground;
import com.seniorproject.game.ShooterGame;

public class BaseMenu extends BaseScreen {

	protected ShooterGame game;
	protected Stage stage;
	
	protected SpriteBatch batch;
	protected Sprite menuBorder;
	
	protected LevelBackground levelBG;
	
	public BaseMenu(ShooterGame g) {
		game = g;
	}
	
	
	public void init(String menuImage, int borderOffset) {
		
		batch = new SpriteBatch();
		stage = new Stage();

		setGameStage(stage);
		
		Texture menuBorderTexture = new Texture(Gdx.files.internal(menuImage));
		
		levelBG = new LevelBackground(.00005f);
	
		menuBorder = new Sprite(menuBorderTexture);
		menuBorder.setPosition((stage.getWidth()/2)-(menuBorder.getWidth()/2), (stage.getHeight()/2)-(menuBorder.getHeight()/2)-borderOffset);
				
		stage.addActor(levelBG);
		
	}
	
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		
		if(levelBG != null) {
			levelBG.dispose();
		}
		
		if(menuBorder != null) {
			menuBorder.getTexture().dispose();
		}
	}

}
