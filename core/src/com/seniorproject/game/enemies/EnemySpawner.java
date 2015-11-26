package com.seniorproject.game.enemies;

import java.util.ArrayList;

import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.levels.Level;

public class EnemySpawner extends Spawner {

	int maxEnemiesAtOnce;

	boolean spawnedBoss = false;
	
	ArrayList<Enemy> enemyList;
	ArrayList<Enemy> deadEnemyList;
	
	
	Boss boss;
	
	
	public EnemySpawner(Level l, String enemySpriteFile) {
		super(l, enemySpriteFile);

		this.enemySpriteFile = enemySpriteFile;
		
		enemyList = new ArrayList<Enemy>();
		deadEnemyList = new ArrayList<Enemy>();
		
		String bossFile = "boss.png";
		if(ShooterGame.CURRENT_LEVEL == -1) {
			bossFile = "boss2.png";
		}
		
		boss = new Boss(l, bossFile);
		
		maxEnemies = 15;
		maxEnemiesAtOnce = 10;
		totalEnemies = 0;
		spawnRate = 3;
		lastSpawnTime = 0;
		enemiesToSpawnEachInterval = 5;
		enemySpawnIntervalCount = 0;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		// Reset the amount spawned this interval if enough time has passed
		if((getSeconds()-lastSpawnTime) > spawnRate) {
			enemySpawnIntervalCount = 0;
		}
		
		
		// Adds enemies to the stage
		if(getStage() != null && totalEnemies < maxEnemies && enemiesToSpawnEachInterval > enemySpawnIntervalCount && enemyList.size() < maxEnemiesAtOnce) {
			level = (Level) getStage();
			
			
			Enemy enemy = new Enemy(level, enemySpriteFile);			
			enemyList.add(enemy);
			level.addGameObject(enemy);
			totalEnemies++;
			enemySpawnIntervalCount++;
			lastSpawnTime = getSeconds();
		}
		
		// Remove dead enemies from the list
		for(Enemy e : enemyList) {
			if(e.isDead()) {
				deadEnemyList.add(e);
			}
		}
		
		for(Enemy e : deadEnemyList) {
			if(e.isDead()) {
				enemyList.remove(e);
			}
		}
		
		deadEnemyList.clear();
		
		if(level != null && totalEnemies == maxEnemies && enemyList.size() == 0 && !spawnedBoss) {
			// SPAWN BOSS
			
			level.enemyHealthBar.MAX_HEALTH = boss.getHealth();
			level.enemyHealthBar.setHealth(boss.getHealth());
			level.addGameObject(boss);
			spawnedBoss = true;
			
		}
		
		
	}
	
}
