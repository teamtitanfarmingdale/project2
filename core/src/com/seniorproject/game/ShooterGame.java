package com.seniorproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.seniorproject.game.menus.GameOverScreen;
import com.seniorproject.game.menus.MainMenuScreen;
import com.seniorproject.game.menus.VictoryScreen;
import com.seniorproject.game.menus.PlayMenu;

public class ShooterGame extends Game {

	public final static String GAME_NAME = "Space Titans";
	
	public static int GAME_WIDTH = 1200;
	public static int GAME_HEIGHT = 720;

	public static float MUSIC_VOLUME = 0f;
	public static float SFX_VOLUME = .5f;
	
	public static Sound bgMusic;
	
	public static int PLAYER_SCORE = 0;
	public static int PREVIOUS_SCORE = 0;
	
	public static int STARTING_ENEMY_COUNT = 5;
	
	public static int CURRENT_LEVEL = 0;
	
	public static Ship PLAYER_SHIP;
	
	public static PlayerSave PLAYER_SAVE = null;
	public Database db;
	
	public static long soundID;
	
	public AssetManager assetManager;

	// Screen Constants
	public static final int MAIN_MENU = 1;
	public static final int GAME = 2;
	public static final int PAUSE = 3;
	public static final int VICTORY = 4;
	public static final int GAME_OVER = 5;
	public static final int LOADED_GAME = 6;
	public static final int PLAY_MENU = 7;
	
	// Default Skin
	public Skin defaultSkin;
	
	private Screen currentScreen;
	private GameScreen currentGameScreen;
	
	@Override
	public void create() {
		
		db = new Database();
		
		assetManager = new AssetManager();
		
		defaultSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		bgMusic = Gdx.audio.newSound(Gdx.files
				.internal("sounds/action.wav"));
		soundID = bgMusic.loop(ShooterGame.MUSIC_VOLUME);
		
		
		currentScreen = new MainMenuScreen(this);
		
		this.setScreen(currentScreen);
		
		
		
	}

	
	public void switchScreen(int screen) {
		switch(screen) {
			case MAIN_MENU:
				currentScreen.dispose();
				currentScreen = new MainMenuScreen(this);
				CURRENT_LEVEL = 0;
				PLAYER_SCORE = 0;
				this.setScreen(currentScreen);
				break;
			case PLAY_MENU:
				currentScreen.dispose();
				currentScreen = new PlayMenu(this);
				this.setScreen(currentScreen);
				break;
			case GAME:
				
				CURRENT_LEVEL++;
			case LOADED_GAME:
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
	
	public static void setBGMusicVolume(float volume) {
		MUSIC_VOLUME = volume;
		bgMusic.setVolume(soundID, volume);
	}
	
	public Screen getCurrentScreen() {
		return currentScreen;
	}
	
	public GameScreen getCurrentGameScreen() {
		return currentGameScreen;
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		
		this.db.disconnect();
	}
}
