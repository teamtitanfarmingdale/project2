package com.seniorproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemySpawner extends Spawner {

	int maxEnemiesAtOnce;

	ArrayList<Enemy> enemyList;
	ArrayList<Enemy> deadEnemyList;
	
	
	public EnemySpawner(World world, String enemySpriteFile) {
		super(world, enemySpriteFile);
		this.world = world;
		this.enemySpriteFile = enemySpriteFile;
		
		enemyList = new ArrayList<Enemy>();
		deadEnemyList = new ArrayList<Enemy>();
		

		maxEnemies = 30;
		maxEnemiesAtOnce = 10;
		totalEnemies = 0;
		spawnRate = 3;
		lastSpawnTime = 0;
		enemiesToSpawnEachInterval = 2;
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
			Enemy enemy = new Enemy(world, enemySpriteFile);
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
		
	}
	
}
