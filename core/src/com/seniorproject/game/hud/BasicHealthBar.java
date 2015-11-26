package com.seniorproject.game.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.seniorproject.game.ShooterGame;

public class BasicHealthBar extends BaseHUD {

	public int MAX_HEALTH = 100;
	
	private Sprite barSprite;
	protected float totalHealth = 100;
	protected float healthBarWidth;
	protected boolean positionSet;
	
	protected int healthBarHeight = 17;
	
	public BasicHealthBar(ShooterGame g) {
		super(g);
	}
	
	public void setHealth(float amount) {
		
		if(amount > MAX_HEALTH) {
			amount = MAX_HEALTH;
		}
		else if(amount < 0) {
			amount = 0;
		}
		
		totalHealth = amount;
		barSprite.setSize((totalHealth/MAX_HEALTH)*healthBarWidth, healthBarHeight);
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
