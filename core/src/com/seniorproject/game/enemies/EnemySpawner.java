package com.seniorproject.game.enemies;

import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.Spawner;
import com.seniorproject.game.levels.Level;

public class EnemySpawner extends Spawner {

	int maxEnemiesAtOnce;

	public boolean spawnedBoss = false;
		
	public Boss boss;
	
	
	public EnemySpawner(Level l) {
		super(l, l.enemySpriteFile);

		String bossFile = "boss.png";
		if(ShooterGame.CURRENT_LEVEL == -1) {
			bossFile = "boss2.png";
		}
		
		boss = new Boss(l, bossFile);
		
		maxItems = 15;
		maxEnemiesAtOnce = 10;
		totalItems = 0;
		spawnRate = 3;
		lastSpawnTime = 0;
		itemsToSpawnEachInterval = 5;
		itemSpawnIntervalCount = 0;
	}
	
	@Override
	public boolean shouldSpawnItem() {
		return super.shouldSpawnItem() && itemList.size() < maxEnemiesAtOnce;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(shouldSpawnItem()) {
			spawnItem(new Enemy(level, itemSpriteFile));
		}
		
		updateItemList();
		
		if(level != null && totalItems == maxItems && itemList.size() == 0 && !spawnedBoss) {
			// SPAWN BOSS
			
			level.enemyHealthBar.MAX_HEALTH = boss.getHealth();
			level.enemyHealthBar.setHealth(boss.getHealth());
			level.addGameObject(boss);
			spawnedBoss = true;
			
		}
		
		
	}
	
}
