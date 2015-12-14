package com.seniorproject.game.weapons;

import com.seniorproject.game.levels.Level;

public class RocketSlot extends WeaponSlot {

	public RocketSlot(Level l) {
		super(l, l.weaponSlotGroup);
		ammoCount = l.ship.totalRockets;
		this.setPosition(x, l.weaponSlotGroup.laserSlot.getY()-yBuffer);
		ammoLabel.setPosition(x, this.getY()-(ammoLabel.getHeight()+5));
		labelBG.setPosition(ammoLabel.getX(), ammoLabel.getY());
		ammoLabel.setText(""+ammoCount);
		deselect();
	}

	@Override
	public void select() {
		this.weaponSlotRegion.setRegion(0, (height*2), width, height);
		weaponGroup.selectedWeapon = this;
	}

	@Override
	public void deselect() {
		if(!disabled()) {
			this.weaponSlotRegion.setRegion(0, (height*3), width, height);
		}
	}

	@Override
	public void disable() {
		super.disable();
		this.weaponSlotRegion.setRegion(0, (height*4), width, height);
		weaponGroup.laserSlot.select();
	}

	@Override
	public void shoot() {
		Rocket bomb = new Rocket(level);
		bomb.setPosition(level.ship.getX()+(level.ship.getSprite().getWidth()/2)-(bomb.bulletSprite.getWidth()/2), 
				level.ship.getY()+level.ship.getSprite().getHeight());
		level.addGameObject(bomb);
		lowerAmmo();
		
	}

	@Override
	public void lowerAmmo() {
		super.lowerAmmo();
		
		level.ship.totalRockets = ammoCount;
	}
	
	@Override
	public void addAmmo(int amount) {
		super.addAmmo(amount);
		
		level.ship.totalRockets = ammoCount;
	}
	
}
