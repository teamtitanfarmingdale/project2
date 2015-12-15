package com.seniorproject.game.weapons;

import com.seniorproject.game.Ship;
import com.seniorproject.game.levels.Level;

public class EMPSlot extends WeaponSlot {
	
	public EMPSlot(Level l) {
		super(l, l.weaponSlotGroup);
		this.setPosition(x, weaponGroup.bombSlot.getY()-yBuffer);
		ammoLabel.setPosition(x, this.getY()-(ammoLabel.getHeight()+5));
		labelBG.setPosition(ammoLabel.getX(), ammoLabel.getY());
		ammoCount = l.ship.totalEMP;
		
		if(ammoCount == 0) {
			disable();
		}
		
		ammoLabel.setText(""+ammoCount);
		deselect();
	}

	@Override
	public void select() {
		weaponSlotRegion.setRegion(0, height*5, width, height);
		weaponGroup.selectedWeapon = this;
	}

	@Override
	public void deselect() {
		if(!disabled()) {
			weaponSlotRegion.setRegion(0, height*6, width, height);
		}
	}

	@Override
	public void disable() {
		super.disable();
		weaponSlotRegion.setRegion(0, height*7, width, height);
		weaponGroup.laserSlot.select();
	}

	@Override
	public void shoot() {
		EMP emp = new EMP(level);
		emp.setPosition(level.ship.getX()+(emp.getWidth()/2)-(level.ship.getWidth()), level.ship.getY());
		level.addGameObject(emp);
		lowerAmmo();
	}
	
	@Override
	public void lowerAmmo() {
		super.lowerAmmo();
		
		level.ship.totalEMP = ammoCount;
	}
	
	@Override
	public void addAmmo(int amount) {
		super.addAmmo(amount);
		
		level.ship.totalEMP = ammoCount;
	}
	
}
