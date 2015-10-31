package com.seniorproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.screens.GameScreen;


public class ShooterGame extends Game {

	private Level stage;

	public static int GAME_WIDTH = 1200;
	public static int GAME_HEIGHT = 720;

	@Override
	public void create() {
		
		this.setScreen(new GameScreen());

	}


}
