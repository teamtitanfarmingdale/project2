package com.seniorproject.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.seniorproject.game.ShooterGame;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ShooterGame.GAME_WIDTH;
		config.height = ShooterGame.GAME_HEIGHT;
		new LwjglApplication(new ShooterGame(), config);
	}
}
