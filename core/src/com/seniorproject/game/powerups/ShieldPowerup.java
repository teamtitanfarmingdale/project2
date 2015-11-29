package com.seniorproject.game.powerups;

import com.seniorproject.game.levels.Level;

public class ShieldPowerup extends BasePowerup {

	public ShieldPowerup(Level l) {
		super(l);
		actualHeight = 44;
		sprite.setRegion(0, spriteHeight*3, spriteWidth, actualHeight);
		sprite.setSize(spriteWidth, actualHeight);
		setParticleColor(Powerup.SHIELD_COLOR);
	}

	
	@Override
	public void applyPowerup() {
		level.ship.applyShield();
	}

}
