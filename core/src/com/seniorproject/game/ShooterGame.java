package com.seniorproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.seniorproject.game.menus.GameOverScreen;
import com.seniorproject.game.menus.MainMenuScreen;
import com.seniorproject.game.menus.VictoryScreen;


public class ShooterGame extends Game {

	public static int GAME_WIDTH = 1200;
	public static int GAME_HEIGHT = 720;

	public static float MUSIC_VOLUME = 0;
	public static float SFX_VOLUME = 1;
	
	public static int PLAYER_SCORE = 0;
	
	public static int STARTING_ENEMY_COUNT = 5;
	
	public static int CURRENT_LEVEL = 0;
	
	public static Ship PLAYER_SHIP;
	
	
	// Screen Constants
	public static final int MAIN_MENU = 1;
	public static final int GAME = 2;
	public static final int PAUSE = 3;
	public static final int VICTORY = 4;
	public static final int GAME_OVER = 5;
	
	
	private Screen currentScreen;
	private GameScreen currentGameScreen;
	
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
				CURRENT_LEVEL++;
				currentScreen.dispose();
				currentScreen = new GameScreen(this);
				currentGameScreen = (GameScreen) currentScreen;
				this.setScreen(currentScreen);
				break;
			case VICTORY:
				currentScreen.dispose();
				currentScreen = new VictoryScreen(this);
				this.setScreen(currentScreen);
				break;
			case GAME_OVER:
				currentScreen.dispose();
				currentScreen = new GameOverScreen(this);
				this.setScreen(currentScreen);
				break;
		}
		
		
	}
	
	public Screen getCurrentScreen() {
		return currentScreen;
	}
	

}
