package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.ButtonHelper;
import com.seniorproject.game.LabelHelper;
import com.seniorproject.game.ShooterGame;

public class GameOverScreen extends BaseMenu {

	ButtonHelper newGameButtonHelper;
	ButtonHelper quitButtonHelper;
	
	LabelHelper finalScoreLabelHelper;
	
	Sprite finalScoreText;
	
	public GameOverScreen(ShooterGame g) {
		super(g);	
		create();
	}
	
	public void create() {
		
		int scoreYOffset = 40;
		int scoreXOffset = 30;
		int buttonOffset = 20;
		
		init("menu/gameover.png", 0);
		
		// FINAL SCORE TEXT
		Texture finalScoreTexture = new Texture(Gdx.files.internal("menu/score-text.png"));
		finalScoreText = new Sprite(finalScoreTexture);
		finalScoreText.setPosition((stage.getWidth()/2)-(finalScoreText.getWidth()+scoreXOffset), (stage.getHeight()/2)+(finalScoreText.getHeight()+scoreYOffset));
		
		// FINAL SCORE LABEL
		finalScoreLabelHelper = new LabelHelper(String.format("%,d", ShooterGame.PLAYER_SCORE), 18, Color.YELLOW);
		Label scoreLabel = finalScoreLabelHelper.getLabel();
		scoreLabel.setWidth(240);
		scoreLabel.setHeight(finalScoreText.getHeight());
		scoreLabel.setPosition((stage.getWidth()/2)-(scoreLabel.getWidth()/2), finalScoreText.getY());
		scoreLabel.setAlignment(Align.right);
		
		
		// BUTTONS
		newGameButtonHelper = new ButtonHelper("menu/newgame-button.png", 204, 63, 0, 0, 0, 63);
		ImageButton newGameButton = newGameButtonHelper.getButton();

		quitButtonHelper = new ButtonHelper("menu/quit-button.png", 204, 63, 0, 0, 0, 63);
		ImageButton quitButton = quitButtonHelper.getButton();
		
		newGameButton.setPosition((stage.getWidth()/2)-(newGameButton.getWidth()/2), (stage.getHeight()/2)-(newGameButton.getHeight()/2)-buttonOffset);
		quitButton.setPosition((stage.getWidth()/2)-(quitButton.getWidth()/2), newGameButton.getY()-quitButton.getHeight()-buttonOffset);
		
		
		// BUTTON EVENTS
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ShooterGame.CURRENT_LEVEL = 0;
				ShooterGame.PLAYER_SCORE = 0;
				game.switchScreen(ShooterGame.GAME);
			}
		});
		
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.switchScreen(ShooterGame.MAIN_MENU);
			}
		});
		
		stage.addActor(scoreLabel);
		stage.addActor(newGameButton);
		stage.addActor(quitButton);
		
		Gdx.input.setInputProcessor(stage);
		
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		
		this.batch.begin();
		this.menuBorder.draw(batch);
		this.finalScoreText.draw(batch);
		this.batch.end();
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		finalScoreText.getTexture().dispose();
		newGameButtonHelper.dispose();
		quitButtonHelper.dispose();
	}
}
