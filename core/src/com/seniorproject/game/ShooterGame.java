package com.seniorproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.menus.GameOverScreen;
import com.seniorproject.game.menus.LoginDialog;
import com.seniorproject.game.menus.MainMenuScreen;
import com.seniorproject.game.menus.VictoryScreen;
import com.seniorproject.game.menus.PlayMenu;

public class ShooterGame extends Game {

	public final static String GAME_NAME = "Space Titans";
	
	public static int GAME_WIDTH = 1200;
	public static int GAME_HEIGHT = 720;

	public static float MUSIC_VOLUME = 0f;
	public static float SFX_VOLUME = .5f;
	
	public static int MUSIC_INDEX = 0;
	public static int GAME_MUSIC_INDEX = 1;
	public static int MENU_MUSIC_INDEX = 0;
	
	public static Sound[] bgMusic;
	
	public static int PLAYER_SCORE = 0;
	public static int PREVIOUS_SCORE = 0;
	
	public static int STARTING_ENEMY_COUNT = 5;
	
	public static int CURRENT_LEVEL = 0;
	
	public static int NEXT_LEVEL_ID = 1;
	
	public static Ship PLAYER_SHIP;
	
	public static final int MAX_LIVES = 10;
	
	public static PlayerSave PLAYER_SAVE = null;
	public Database db;
	
	public static long[] soundID;
	
	public AssetManager assetManager;
	
	public static int PLAYER_ID = 0;

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
	
	private BaseScreen currentScreen;
	private GameScreen currentGameScreen;
	
	private LoginDialog loginDialog;
	
	@Override
	public void create() {
		
		db = new Database();

		assetManager = new AssetManager();
		
		defaultSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		bgMusic = new Sound[2];
		
		bgMusic[0] = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menu_victory.wav"));
		
		bgMusic[1] = Gdx.audio.newSound(Gdx.files
				.internal("sounds/action.wav"));
		
		
		soundID = new long[2];
		soundID[0] = bgMusic[0].loop(ShooterGame.MUSIC_VOLUME);
		
		soundID[1] = bgMusic[1].loop(0);
		bgMusic[1].pause();
		
		currentScreen = new MainMenuScreen(this);
		
		loginDialog = new LoginDialog(currentScreen.getStage(), this);
		
		this.setScreen(currentScreen);
		
	}

	
	public void switchScreen(int screen) {
		
		// Reset Victory Screen for login dialog
		loginDialog.setVictoryScreen(null);
		
		switch(screen) {
			case MAIN_MENU:
				currentScreen.dispose();
				currentScreen = new MainMenuScreen(this);
				CURRENT_LEVEL = 0;
				PLAYER_SCORE = 0;
				MUSIC_INDEX = 0;
				this.setScreen(currentScreen);
				break;
			case PLAY_MENU:
				MUSIC_INDEX = 0;
				currentScreen.dispose();
				currentScreen = new PlayMenu(this);
				this.setScreen(currentScreen);
				break;
			case GAME:
				MUSIC_INDEX = 1;
				CURRENT_LEVEL++;
			case LOADED_GAME:
				currentScreen.dispose();
				currentScreen = new GameScreen(this);
				currentGameScreen = (GameScreen) currentScreen;
				this.setScreen(currentScreen);
				break;
			case VICTORY:
				MUSIC_INDEX = 0;
				currentScreen.dispose();
				currentScreen = new VictoryScreen(this);
				this.setScreen(currentScreen);
				break;
			case GAME_OVER:
				MUSIC_INDEX = 1;
				currentScreen.dispose();
				currentScreen = new GameOverScreen(this);
				this.setScreen(currentScreen);
				break;
		}
		
		loginDialog.setParentStage(currentScreen.getStage());
		
		setBGMusicVolume(MUSIC_VOLUME);
	}
	
	public static void setBGMusicVolume(float volume) {
		MUSIC_VOLUME = volume;
		bgMusic[MUSIC_INDEX].resume();
		bgMusic[MUSIC_INDEX].setVolume(soundID[MUSIC_INDEX], volume);
		
		int mutedIndex = MUSIC_INDEX == 0 ? 1 : 0;
		bgMusic[mutedIndex].setVolume(soundID[mutedIndex], 0);
		bgMusic[mutedIndex].pause();
	}
	
	public Screen getCurrentScreen() {
		return currentScreen;
	}
	
	public GameScreen getCurrentGameScreen() {
		return currentGameScreen;
	}
	
	public void showLoginDialog() {
		System.out.println("test");
		loginDialog.show();
	}
	
	public LoginDialog getLoginDialog() {
		return loginDialog;
	}
	
	public void drawLoginDialog() {
		
		if(loginDialog.isVisible()) {
			loginDialog.getStage().act(Gdx.graphics.getDeltaTime());
			loginDialog.getStage().draw();
		}
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		this.db.disconnect();
	}
}
