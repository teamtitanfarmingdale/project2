package com.seniorproject.game.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.levels.Level;

public abstract class WeaponSlot extends Image {

	protected Level level;
	protected int ammoCount = 0;
	protected int width = 54;
	protected int height = 54;
	protected int xBuffer = width+20;
	protected int yBuffer = height+30;
	protected WeaponSlotGroup weaponGroup;
	protected float x;
	protected float startingY;
	protected boolean disabled = false;
	protected Label ammoLabel;
	protected Image labelBG;
	
	protected Texture weaponSlots;
	protected TextureRegion weaponSlotRegion;
	protected TextureRegionDrawable weaponSlotDrawable;
	
	public WeaponSlot(Level l, WeaponSlotGroup group) {
		level = l;
		weaponGroup = group;
		weaponSlots = level.game.assetManager.getTexture("weapon-slots.png");
		weaponSlotRegion = new TextureRegion(weaponSlots);
		weaponSlotDrawable = new TextureRegionDrawable(weaponSlotRegion);
		this.setDrawable(weaponSlotDrawable);
		this.setWidth(width);
		this.setHeight(height);
		//this.setDebug(true);
		
		x = l.getWidth()-xBuffer;
		startingY = l.getHeight()-(l.getHeight()/3.5f);
		
		// Ammo Count Label BG
		Texture fadedBG = level.game.assetManager.getTexture("faded-black-bg.png");
		fadedBG.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		labelBG = new Image(fadedBG);
		labelBG.setWidth(54);
		labelBG.setHeight(16);
		group.addActor(labelBG);
		
		
		// Ammo Count Label
		LabelHelper ammoLabelHelper = new LabelHelper(""+ammoCount+"", 12, Color.WHITE, "vcr.ttf", level.game);
		ammoLabel = ammoLabelHelper.getLabel();
		ammoLabel.setWidth(width);
		ammoLabel.setHeight(16);
		//ammoLabel.getLabel().setDebug(true);
		ammoLabel.setAlignment(Align.center);
		group.addActor(ammoLabel);
		
	}
	
	
	public void lowerAmmo() {
		ammoCount--;
		ammoLabel.setText(""+ammoCount);
		if(ammoCount == 0) {
			disable();
		}
	}
	
	public void addAmmo(int amount) {
		ammoCount += amount;
		ammoLabel.setText(""+ammoCount);
		
		if(disabled && ammoCount > 0) {
			enable();
		}
		
	}
	
	public int getAmmoCount() {
		return ammoCount;
	}
	
	public void enable() {
		disabled = false;
		deselect();
	}
	
	public void disable() {
		disabled = true;
	}
	
	public boolean disabled() {
		return disabled;
	}
	
	public abstract void select();
	public abstract void deselect();
	public abstract void shoot();
}
