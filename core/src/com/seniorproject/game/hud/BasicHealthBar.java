package com.seniorproject.game.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BasicHealthBar extends BaseHUD {

	private static final int MAX_HEALTH = 100;
	
	private Sprite barSprite;
	protected float totalHealth = 100;
	protected float healthBarWidth;
	protected boolean positionSet;
	
	public void setHealth(float amount) {
		
		if(amount > MAX_HEALTH) {
			amount = MAX_HEALTH;
		}
		else if(amount < 0) {
			amount = 0;
		}
		
		totalHealth = amount;

		
		barSprite.setSize((totalHealth/MAX_HEALTH)*healthBarWidth, 17);
		positionSet = false;
	}
	
	public void lowerHealth(float amount) {
		
		totalHealth -= amount;
		
		if(totalHealth < 0) {
			totalHealth = 0;
		}
		
		setHealth(totalHealth);
		
	}
	
	public void setHealthBarSprite(Sprite sprite) {
		barSprite = sprite;
		healthBarWidth = barSprite.getWidth();
	}
	
	public boolean isPositionSet() {
		return positionSet;
	}
	
	public void setPositionSet(boolean set) {
		positionSet = set;
	}
}
