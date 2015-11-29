package com.seniorproject.game.powerups;

import com.seniorproject.game.levels.Level;

public class LifePowerup extends BasePowerup {

	public LifePowerup(Level l) {
		super(l);
		actualHeight = 41;
		sprite.setRegion(0, spriteHeight*2, spriteWidth, actualHeight);
		sprite.setSize(spriteWidth, actualHeight);
		setParticleColor(Powerup.LIFE_COLOR);
	}

	
	
	
	@Override
	public void applyPowerup() {
		level.healthBar.lifeManager.addLife();
	}

}
