package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.seniorproject.game.levels.Level;

public class GameScreen implements Screen {

	
	private Level stage;
	public ShooterGame game;
	public boolean gamePaused = false;
	
	
	public GameScreen(ShooterGame g) {
		game = g;
		create();
	}
	
	
	public void create() {
		
		stage = new Level();
		stage.setScreen(this);
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
		if(!gamePaused) {
			
			stage.act(Gdx.graphics.getDeltaTime());
			
		}
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
		gamePaused = !gamePaused;
		
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
		if (stage != null) {
			stage.dispose();
		}
	}

}
