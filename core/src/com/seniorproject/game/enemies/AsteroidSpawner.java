package com.seniorproject.game.enemies;

import com.seniorproject.game.GameActor;
import com.seniorproject.game.Spawner;
import com.seniorproject.game.levels.Level;

public class AsteroidSpawner extends Spawner {

	public AsteroidSpawner(Level l) {
		super(l, l.asteroidSpriteFile);
		
		maxItems = 100;
		totalItems = 0;
		spawnRate = 4;
		lastSpawnTime = 2;
		itemsToSpawnEachInterval = 4;
		itemSpawnIntervalCount = 0;
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(!stopped && shouldSpawnItem()) {
			spawnItem(new Asteroid(level, itemSpriteFile));
			// Reset total item count so an infinite amount will spawn
			totalItems = 0;
		}
		updateItemList();
	}
	
	@Override
	public void stop() {
		totalItems = maxItems;
		BaseEnemy e;
		stopped = true;
		for(GameActor a : itemList) {
			e = (BaseEnemy) a;
			e.silentDeath(true);
			System.out.println("KILLED ASTEROID");
		}
		
	}
	
}
