package com.seniorproject.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.seniorproject.game.Ship;
import com.seniorproject.game.levels.Level;

public class LaserSlot extends WeaponSlot {

	Image infiniteImage;
	
	public LaserSlot(Level l) {
		super(l, l.weaponSlotGroup);
		ammoCount = 100;
		this.setPosition(x, startingY);
		ammoLabel.setText("\u221E");
		ammoLabel.setPosition(x, startingY-(ammoLabel.getHeight()+5));
		labelBG.setPosition(ammoLabel.getX(), ammoLabel.getY());
		
		// Show Infinite Symbol
		Texture infiniteSymbol = level.game.assetManager.getTexture("infinite.png");
		infiniteImage = new Image(infiniteSymbol);
		infiniteImage.setWidth(16);
		infiniteImage.setHeight(8);
		
		infiniteImage.setPosition(labelBG.getX()+(labelBG.getWidth()/2)-(infiniteImage.getWidth()/2), labelBG.getY()+(labelBG.getHeight()/2)-(infiniteImage.getHeight()/2));
		weaponGroup.addActor(infiniteImage);
		
		select();
	}

	@Override
	public void select() {
		weaponSlotRegion.setRegion(0, 0, width, height);
		weaponGroup.selectedWeapon = this;
	}

	@Override
	public void deselect() {
		weaponSlotRegion.setRegion(0, height, width, height);
	}

	@Override
	public void disable() {
		// Don't disable
	}
	
	@Override
	public void lowerAmmo() {
		// Do nothing
	}

	@Override
	public void shoot() {
		
		Bullet bullet = new Bullet(level, level.ship.getX(), level.ship.getY());
		
		bullet.setX(level.ship.getX()+(level.ship.getWidth()/2)-(bullet.getWidth()/2));
		bullet.setY(level.ship.getY()+(level.ship.getHeight()/2));

		level.addGameObject(bullet);
		
	}

	
}
