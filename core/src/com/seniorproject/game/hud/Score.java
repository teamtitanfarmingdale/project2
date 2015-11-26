package com.seniorproject.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.levels.Level;

public class Score extends Actor {

	
	Texture backgroundTexture;
	Sprite backgroundSprite;
	
	LabelHelper labelHelper;
	
	int score = 0;
	
	boolean positionSet = false;
	
	public Score(ShooterGame g) {
		
		score = ShooterGame.PLAYER_SCORE;
		
		backgroundTexture = g.assetManager.getTexture("hud/score/bg.png");
		backgroundSprite = new Sprite(backgroundTexture);
		
		labelHelper = new LabelHelper(""+score+"", 30, Color.GOLD, g);
		
		labelHelper.getLabel().setWidth(backgroundSprite.getWidth());
		labelHelper.getLabel().setAlignment(Align.center);
		
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		
		
		if(!positionSet && getParent().getStage() != null) {
			positionSet = true;
			
			float x = (getParent().getStage().getWidth()/2)-(backgroundSprite.getWidth()/2);
			float y = (getParent().getStage().getHeight()-(backgroundSprite.getHeight()));
			Level level = (Level) getParent().getStage();
			
			backgroundSprite.setPosition(x, y);
			
			labelHelper.getLabel().setPosition(x, y+labelHelper.getLabel().getHeight()-(backgroundSprite.getHeight()/10));
			
			level.addHUDObject(labelHelper.getLabel());
			
		}
		
		
		backgroundSprite.draw(batch);

	}
	
	public float getBGWidth() {
		return backgroundSprite.getWidth();
	}
	
	public float getBGHeight() {
		return backgroundSprite.getHeight();
	}
	
	public void addToScore(int amount) {
		setScore(score+amount);
	}
	
	public void setScore(int score) {
		this.score = score;
		labelHelper.getLabel().setText(String.format("%,d", score));
	}
	
	public int getScore() {
		return score;
	}
}
