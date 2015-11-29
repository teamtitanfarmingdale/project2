package com.seniorproject.game.powerups;

import com.seniorproject.game.levels.Level;

public class RocketPowerup extends BasePowerup {

	public RocketPowerup(Level l) {
		super(l);
		actualHeight = 43;
		sprite.setRegion(0, spriteHeight*1, spriteWidth, actualHeight);
		sprite.setSize(spriteWidth, actualHeight);
		
		setParticleColor(Powerup.ROCKET_COLOR);
	}

	
	@Override
	public void applyPowerup() {
		level.weaponSlotGroup.bombSlot.addAmmo(1);	
	}

	
}
