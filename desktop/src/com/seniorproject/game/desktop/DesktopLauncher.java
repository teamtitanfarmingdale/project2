package com.seniorproject.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.seniorproject.game.ShooterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ShooterGame.GAME_WIDTH;
		config.height = ShooterGame.GAME_HEIGHT;
		config.title = "Space Titans";
		config.resizable = false;
		config.addIcon("icon.png", FileType.Internal);
		new LwjglApplication(new ShooterGame(), config);
	}
}
