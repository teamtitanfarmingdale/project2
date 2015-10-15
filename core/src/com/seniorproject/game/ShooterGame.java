package com.seniorproject.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class ShooterGame extends ApplicationAdapter {
	
	private Level stage;
	
	public static int GAME_WIDTH = 560;
	public static int GAME_HEIGHT = 700;
	
	BitmapFont myFont;
	
	@Override
	public void create () {
		
		stage = new Level();
		Gdx.input.setInputProcessor(stage);		
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	
	@Override
	public void dispose() {
		if(stage != null) {
			stage.dispose();
		}
	}

	
}
