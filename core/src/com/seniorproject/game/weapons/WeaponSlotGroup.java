package com.seniorproject.game.weapons;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.levels.Level;

public class WeaponSlotGroup extends Group {

	protected Level level;
	public LaserSlot laserSlot;
	public RocketSlot bombSlot;
	public EMPSlot empSlot;
	
	public WeaponSlot selectedWeapon;
	
	public WeaponSlotGroup(Level l) {
		super();
		level = l;
	}
	
	public void init() {
		laserSlot = new LaserSlot(level);
		bombSlot = new RocketSlot(level);
		empSlot = new EMPSlot(level);
		this.addActor(laserSlot);
		this.addActor(bombSlot);
		this.addActor(empSlot);
		
		selectedWeapon = laserSlot;
		
		// Selector Listener
		level.addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				selectSlot(keycode, 1);
				return true;
			}
			
			@Override
			public boolean scrolled(InputEvent event, float x, float y, int amount) {
				if(amount == 1) {
					// Scroll Down
					switch(selectedWeapon.getClass().getSimpleName()) {
						case "RocketSlot":
							selectSlot(Keys.NUM_3, amount);
							break;
						case "LaserSlot":
							selectSlot(Keys.NUM_2, amount);
							break;
						case "EMPSlot":
							selectSlot(Keys.NUM_1, amount);
							break;
					}
					
					
				}
				else {
					// Scroll Up
					switch(selectedWeapon.getClass().getSimpleName()) {
						case "RocketSlot":
							selectSlot(Keys.NUM_1, amount);
							break;
						case "LaserSlot":
							selectSlot(Keys.NUM_3, amount);
							break;
						case "EMPSlot":
							selectSlot(Keys.NUM_2, amount);
							break;
					}
				}
				return true;
			}
			
		});
		
		
		// Shooting Listener
		
		level.addListener(new ClickListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				if(!level.screen.gamePaused && button == Input.Buttons.LEFT && !level.levelFinished) {
					if(selectedWeapon.getAmmoCount() > 0) {
						selectedWeapon.shoot();
					}
				}

				return true;
			}
			
		});
		
		
		
	}
	
	public void selectSlot(int keycode, int direction) {
		switch(keycode) {
			case Keys.NUM_1:
				laserSlot.select();
				bombSlot.deselect();
				empSlot.deselect();
				selectedWeapon = laserSlot;
				break;
			case Keys.NUM_2:
				if(!bombSlot.disabled()) {
					laserSlot.deselect();
					bombSlot.select();
					empSlot.deselect();
					selectedWeapon = bombSlot;
				}
				else if(direction == 1) {
					// down -select emp
					selectSlot(Keys.NUM_3, direction);
				}
				else {
					// up - select laser
					selectSlot(Keys.NUM_1, direction);
				}
				
				break;
			case Keys.NUM_3:
				if(!empSlot.disabled()) {
					laserSlot.deselect();
					bombSlot.deselect();
					empSlot.select();
					selectedWeapon = empSlot;
				}
				else if(direction == 1) {
					// down - select laser
					selectSlot(Keys.NUM_1, direction);
				}
				else {
					// up - select bomb
					selectSlot(Keys.NUM_2, direction);
				}
				
				
				break;
		}
	}
	
}
