package com.seniorproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.seniorproject.game.levels.Level;

public class Spawner extends Actor {

	protected int maxItems;
	protected int totalItems;
	protected String itemSpriteFile;
	protected World world;
	protected Level level;
	
	protected boolean stopped = false;
	
	protected ArrayList<GameActor> itemList;
	protected ArrayList<GameActor> deadItemList;
	
	protected static final int ONE_BILLION = 1000000000;
	
	// Time between each enemy spawning
	protected int spawnRate;
	
	// Time the last enemy was spawned
	protected long lastSpawnTime;
	
	// How many enemies to spawn per spawnRate
	protected int itemsToSpawnEachInterval;
	
	// Total enemies spawned this interval
	protected int itemSpawnIntervalCount;
	
	public Spawner(Level l, String enemySpriteFile) {
		this.level = l;
		this.world = l.world;
		this.itemSpriteFile = enemySpriteFile;
		
		itemList = new ArrayList<GameActor>();
		deadItemList = new ArrayList<GameActor>();
		
	}
	
	public void setMaxItems(int maxEnemies) {
		this.maxItems = maxEnemies;
	}
	
	public static long getSeconds() {
		return System.nanoTime()/ONE_BILLION;
	}
	
	public int getMaxItems() {
		return maxItems;
	}
	
	public void updateItemList() {
		if(itemList.size() > 0) {
			for(GameActor a : itemList) {
				if(a.isDead()) {
					deadItemList.add(a);
				}
			}
			
			if(deadItemList.size() > 0) {
				for(GameActor a : deadItemList) {
					itemList.remove(a);
				}
				
				deadItemList.clear();
			}
			
		}
	}
	
	public boolean shouldSpawnItem() {
		// Reset the amount spawned this interval if enough time has passed
		if((getSeconds()-lastSpawnTime) > spawnRate) {
			itemSpawnIntervalCount = 0;
		}
		
		return (getStage() != null && totalItems < maxItems && itemsToSpawnEachInterval > itemSpawnIntervalCount);
	}
	
	public void spawnItem(GameActor a) {
		
		// Adds enemies to the stage
		if(shouldSpawnItem()) {
			level = (Level) getStage();
			GameActor enemy = a;		
			itemList.add(enemy);
			level.addGameObject(enemy);
			totalItems++;
			itemSpawnIntervalCount++;
			lastSpawnTime = getSeconds();
		}
	}
	
	public ArrayList<GameActor> getItemList() {
		return itemList;
	}
	
	public void stop() {
		totalItems = maxItems;

		for(GameActor a : itemList) {
			a.setDead(true);
		}
	}
	
	
}
