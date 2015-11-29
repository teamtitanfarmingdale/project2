package com.seniorproject.game.powerups;

import com.seniorproject.game.levels.Level;

public class ArmorPowerup extends BasePowerup {

	public ArmorPowerup(Level l) {
		super(l);
		actualHeight = 44;
		sprite.setRegion(0, spriteHeight*4, spriteWidth, actualHeight);
		sprite.setSize(spriteWidth, actualHeight);
		setParticleColor(Powerup.ARMOR_COLOR);
	}

	
	@Override
	public void applyPowerup() {
		level.ship.addArmor(25);
	}

}
