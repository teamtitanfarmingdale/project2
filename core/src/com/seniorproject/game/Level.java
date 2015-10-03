package com.seniorproject.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Level extends Stage {

	Ship ship;
	LevelBackground background;
	
	
	public Level() {
		super(new ScreenViewport());	
		
		Group gameObjects = new Group();
		
		
		background = new LevelBackground();
		ship = new Ship();
		
		gameObjects.addActor(background);
		gameObjects.addActor(ship);
		
		addActor(gameObjects);
		setKeyboardFocus(gameObjects);
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
}
