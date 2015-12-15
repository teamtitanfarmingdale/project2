package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.seniorproject.game.levels.IceLevel;
import com.seniorproject.game.levels.LavaLevel;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.menus.PauseMenu;

public class GameScreen extends BaseScreen {

	
	//private Level stage;
	private PauseMenu pauseMenu;
	
	public ShooterGame game;
	public boolean gamePaused = false;
	
	
	
	public GameScreen(ShooterGame g) {
		game = g;
		create();
		pauseMenu = new PauseMenu(this);
		
	}
	
	
	public void create() {
		
		switch(ShooterGame.NEXT_LEVEL_ID) {
			case 2:
				stage = new IceLevel(game);
				break;
			case 3:
				stage = new LavaLevel(game);
				break;
			default:
				stage = new Level(game);
		}

		
		
		setGameStage(stage);
		
		((Level) stage).setScreen(this);
		Gdx.input.setInputProcessor(stage);
		
	}
	
	public Level getLevel() {
		return (Level) stage;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!gamePaused) {
			
			stage.act(Gdx.graphics.getDeltaTime());
			
		}
		
		
		stage.draw();
		
		
		if(gamePaused) {
			pauseMenu.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
		gamePaused = true;
		Gdx.input.setInputProcessor(pauseMenu.getStage());
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
		gamePaused = false;
		Gdx.input.setInputProcessor(stage);
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
		
		pauseMenu.dispose();
	}
	


}
