package com.seniorproject.game.powerups;

import java.util.ArrayList;
import java.util.Collections;

import com.seniorproject.game.Ship;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.Spawner;
import com.seniorproject.game.levels.Level;

public class PowerupSpawner extends Spawner {
	private ArrayList<Integer> possiblePowerups;
	
	protected BasePowerup powerup;
	
	public PowerupSpawner(Level l) {
		super(l, "");
		spawnRate = 5;
		maxItems = 10;
		itemsToSpawnEachInterval = 1;

		setupPossiblePowerups();
		
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(!stopped && shouldSpawnItem()) {
			int randomNum = -1;
			int selected = -1;
			
			while(selected == -1) {
				try {
					randomNum = getRandomPowerup();
					selected = possiblePowerups.get(randomNum);
					
					// Only get power ups that are useful
					selected = checkRandomPowerup(selected);
					
				}
				catch(Exception e) {
					selected = -1;
				}
			}
			
			powerup = getPowerup(selected);
			spawnItem(powerup);
			
			// Reset total item count so an infinite amount will spawn
			totalItems = 0;
		}
		
		updateItemList();
	}

	private void setupPossiblePowerups() {
		
		possiblePowerups = new ArrayList<Integer>();
		
		int amount = 0;
		for(int i = 1; i<= Powerup.TOTAL_POWERUPS; i++) {
			amount = (int) (Powerup.CHANCES[i]*100);
			for(int x = 1; x <= amount; x++) {
				possiblePowerups.add(i);
			}
		}
		
		Collections.shuffle(possiblePowerups);
		
	}
	
	
	public int getRandomPowerup() {
		int randomNum = (int) Math.ceil((Math.random()*possiblePowerups.size()));
		return randomNum;
	}
	
	public int checkRandomPowerup(int selected) {
		// Only get power ups that are useful
		if(selected == Powerup.LIFE && level.ship.lives == ShooterGame.MAX_LIVES) {
			selected = -1;
		}
		else if(selected == Powerup.HEALTH && level.ship.health == Ship.MAX_HEALTH) {
			selected = -1;
		}
		else if(selected == Powerup.ARMOR && level.ship.armor == Ship.MAX_ARMOR) {
			selected = -1;
		}
		
		return selected;
	}
	
	public BasePowerup getPowerup(int selected) {
		switch(selected) {
			case Powerup.ROCKET:
				powerup = new RocketPowerup(level);
				break;
			case Powerup.EMP:
				powerup = new EMPPowerup(level);
				break;
			case Powerup.HEALTH:
				powerup = new HealthPowerup(level);
				break;
			case Powerup.ARMOR:
				powerup = new ArmorPowerup(level);
				break;
			case Powerup.SHIELD:
				powerup = new ShieldPowerup(level);
				break;
			case Powerup.LIFE:
				powerup = new LifePowerup(level);
				break;
			case Powerup.RANDOM:
				powerup = new RandomPowerup(level);
				break;
		}
		
		return powerup;
	}
	
	
}
