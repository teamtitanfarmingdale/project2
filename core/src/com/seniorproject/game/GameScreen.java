package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.menus.PauseMenu;

public class GameScreen implements Screen {

	
	private Level stage;
	private PauseMenu pauseMenu;
	
	public ShooterGame game;
	public boolean gamePaused = false;
	
	
	
	public GameScreen(ShooterGame g) {
		game = g;
		create();
		pauseMenu = new PauseMenu(this);
		
	}
	
	
	public void create() {
		
		stage = new Level(game);
		stage.setScreen(this);
		Gdx.input.setInputProcessor(stage);
		
	}
	
	public Level getLevel() {
		return stage;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!gamePaused) {
			
			stage.act(Gdx.graphics.getDeltaTime());
			
		}
		
		
		stage.draw();
		
		
		if(gamePaused) {
			pauseMenu.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
		gamePaused = true;
		Gdx.input.setInputProcessor(pauseMenu.getStage());
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
		gamePaused = false;
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (stage != null) {
			stage.dispose();
		}
		
		pauseMenu.dispose();
	}

}
