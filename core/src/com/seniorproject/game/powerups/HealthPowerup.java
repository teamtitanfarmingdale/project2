package com.seniorproject.game.powerups;

import com.seniorproject.game.levels.Level;

public class HealthPowerup extends BasePowerup {

	public HealthPowerup(Level l) {
		super(l);
		actualHeight = 40;
		sprite.setRegion(0, spriteHeight*0, spriteWidth, actualHeight);
		sprite.setSize(spriteWidth, actualHeight);
		setParticleColor(Powerup.HEALTH_COLOR);
	}

	
	@Override
	public void applyPowerup() {
		level.ship.addHealth(25);
	}

}
