package com.seniorproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.menus.MainMenuScreen;


public class ShooterGame extends Game {

	private Level stage;

	public static int GAME_WIDTH = 1200;
	public static int GAME_HEIGHT = 720;

	// Screen Constants
	public static final int MAIN_MENU = 1;
	public static final int GAME = 2;
	
	private Screen currentScreen;
	
	@Override
	public void create() {
		
		currentScreen = new MainMenuScreen(this);
		
		this.setScreen(currentScreen);

	}

	
	public void switchScreen(int screen) {
		
		switch(screen) {
			case MAIN_MENU:
				currentScreen.dispose();
				currentScreen = new MainMenuScreen(this);
				this.setScreen(currentScreen);
				break;
			case GAME:
				currentScreen.dispose();
				currentScreen = new GameScreen();
				this.setScreen(currentScreen);
				break;
		}
		
		
	}
	

}
