package com.seniorproject.game.powerups;

import com.seniorproject.game.levels.Level;

public class EMPPowerup extends BasePowerup {

	public EMPPowerup(Level l) {
		super(l);
		actualHeight = 29;
		sprite.setRegion(0, spriteHeight*6, spriteWidth, actualHeight);
		sprite.setSize(spriteWidth, actualHeight);
		setParticleColor(Powerup.EMP_COLOR);
	}

	
	
	
	@Override
	public void applyPowerup() {
		level.weaponSlotGroup.empSlot.addAmmo(1);	
	}

	
}
