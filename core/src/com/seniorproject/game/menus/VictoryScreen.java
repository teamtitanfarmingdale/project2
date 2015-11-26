package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.LabelHelper;

public class VictoryScreen extends BaseMenu implements Screen {

	LabelHelper scoreLabelHelper;
	LabelHelper bonusLabelHelper;
	ButtonHelper nextLevelButtonHelper;
	ButtonHelper saveButtonHelper;
	ButtonHelper quitButtonHelper;
	
	SubmitScoreDialog submitScoreDialog;
	boolean scoreSubmitted = false;
	
	Sprite dialogSprite;
	
	long lastUpdateTime = 0;
	int playerScoreCounter = 0;
	int bonusScoreCounter = 0;
	int calculatedBonus = 0;
	
	int playerScoreInterval = 1;
	int bonusScoreInterval = 1;
	
	
	public VictoryScreen(ShooterGame g) {
		super(g);
		create();
	}
	
	
	public void create() {
		
		init("menu/victorymenu.png", 0);
		
		submitScoreDialog = new SubmitScoreDialog(stage, game);
		
		int buttonOffset = 10;

		scoreLabelHelper = new LabelHelper(String.format("%,d", 0), 18, Color.YELLOW, game);
		Label scoreLabel = scoreLabelHelper.getLabel();
		scoreLabel.setWidth(240);
		scoreLabel.setPosition((stage.getWidth()/2)-(scoreLabel.getWidth()/2), (stage.getHeight()*.59f));
		scoreLabel.setAlignment(Align.right);
		
		playerScoreInterval = (int) (ShooterGame.PLAYER_SCORE*.03);

		// Calculate Bonus
		
		calculatedBonus = 0;
		if(ShooterGame.PLAYER_SHIP != null) {
			calculatedBonus = (int) ((ShooterGame.PLAYER_SHIP.health+(100*ShooterGame.PLAYER_SHIP.lives)));
			calculatedBonus += ShooterGame.PLAYER_SHIP.armor+(100*ShooterGame.PLAYER_SHIP.lives);
			calculatedBonus *= ((1+ShooterGame.PLAYER_SHIP.totalKills)*.25);
			
			ShooterGame.PLAYER_SHIP.dispose();
		}
		
		submitScoreDialog.setBonusPoints(calculatedBonus);
		
		bonusScoreInterval = (int) (calculatedBonus*.03);
		
		bonusLabelHelper = new LabelHelper(String.format("%,d", 0), 18, Color.YELLOW, game);
		Label bonusLabel = bonusLabelHelper.getLabel();
		bonusLabel.setWidth(240);
		bonusLabel.setPosition((stage.getWidth()/2)-(bonusLabel.getWidth()/2), (stage.getHeight()*.558f));
		bonusLabel.setAlignment(Align.right);
		
		nextLevelButtonHelper = new ButtonHelper("menu/nextlevel-button.png", 204, 63, 0, 0, 0, 63, game);
		
		ImageButton nextLevelButton = nextLevelButtonHelper.getButton();
		nextLevelButton.setPosition((stage.getWidth()/2)-(nextLevelButton.getWidth()/2), bonusLabel.getY()-nextLevelButton.getHeight()-(buttonOffset*2));
		
		saveButtonHelper = new ButtonHelper("menu/submitonline-button.png", 204, 63, 0, 0, 0, 63, game);
		
		ImageButton saveButton = saveButtonHelper.getButton();
		saveButton.setPosition((stage.getWidth()/2)-(saveButton.getWidth()/2), nextLevelButton.getY()-saveButton.getHeight()-buttonOffset);
		
		
		quitButtonHelper = new ButtonHelper("menu/quit-button.png", 204, 63, 0, 0, 0, 63, game);
		
		ImageButton quitButton = quitButtonHelper.getButton();
		quitButton.setPosition((stage.getWidth()/2)-(quitButton.getWidth()/2), saveButton.getY()-quitButton.getHeight()-buttonOffset);
		
		
		// Save Game
		if(ShooterGame.PLAYER_SAVE != null) {
		
			ShooterGame.PLAYER_SAVE.level = ShooterGame.CURRENT_LEVEL+1;
			ShooterGame.PLAYER_SAVE.score = ShooterGame.PLAYER_SCORE + calculatedBonus;
			
			submitScoreDialog.tfHelper.getTextField().setText(ShooterGame.PLAYER_SAVE.name);
			
			game.db.savePlayer(ShooterGame.PLAYER_SAVE);
			
		}
		nextLevelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ShooterGame.PLAYER_SCORE += calculatedBonus;
				game.switchScreen(ShooterGame.GAME);
			}
		});
		
		saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				submitScoreDialog.show();
			}
		});
		
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.switchScreen(ShooterGame.MAIN_MENU);
			}
		});
		
		stage.addActor(scoreLabel);
		stage.addActor(bonusLabel);
		stage.addActor(nextLevelButton);
		stage.addActor(saveButton);
		stage.addActor(quitButton);
		
		
		Gdx.input.setInputProcessor(stage);

	}
	

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		
		batch.begin();
		menuBorder.draw(batch);
		submitScoreDialog.draw(batch);
		batch.end();
		
		if(submitScoreDialog.isVisible()) {
			submitScoreDialog.getStage().act(Gdx.graphics.getDeltaTime());
			submitScoreDialog.getStage().draw();
		}
		
		
		if(System.nanoTime()-lastUpdateTime > .1) {
			lastUpdateTime = System.nanoTime();
			
			if(scoreLabelHelper != null && playerScoreCounter < ShooterGame.PLAYER_SCORE) {
				playerScoreCounter += playerScoreInterval;
				if(playerScoreCounter > ShooterGame.PLAYER_SCORE) {
					playerScoreCounter = ShooterGame.PLAYER_SCORE;
				}
				
				scoreLabelHelper.getLabel().setText(String.format("%,d", playerScoreCounter));
			}
			
			if(bonusLabelHelper != null && bonusScoreCounter < calculatedBonus) {
				bonusScoreCounter += bonusScoreInterval;
				if(bonusScoreCounter > calculatedBonus) {
					bonusScoreCounter = calculatedBonus;
				}
				
				bonusLabelHelper.getLabel().setText(String.format("%,d", bonusScoreCounter));
			}
			
			
		}
		
		
		if(!scoreSubmitted && submitScoreDialog.scoreSubmitted()) {
			// Remove Submit Score Button and move Quit button up
			
			scoreSubmitted = true;
			
			quitButtonHelper.getButton().setPosition(saveButtonHelper.getButton().getX(), saveButtonHelper.getButton().getY());
			saveButtonHelper.getButton().setVisible(false);
			
		}
		
		
	}
	
	public void dispose() {
		super.dispose();
		scoreLabelHelper.dispose();
		bonusLabelHelper.dispose();
		nextLevelButtonHelper.dispose();
		saveButtonHelper.dispose();
		quitButtonHelper.dispose();
		submitScoreDialog.dispose();
	}

	
}
