package com.seniorproject.game;

import com.badlogic.gdx.physics.box2d.World;

public class AsteroidSpawner extends Spawner {

	public AsteroidSpawner(World world, String enemyAsteroidFile) {
		super(world, enemyAsteroidFile);
		
		maxEnemies = 8;
		totalEnemies = 0;
		spawnRate = 4;
		lastSpawnTime = 2;
		enemiesToSpawnEachInterval = 4;
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
		if(getStage() != null && totalEnemies < maxEnemies && enemiesToSpawnEachInterval > enemySpawnIntervalCount) {
			level = (Level) getStage();
			Asteroid asteroid = new Asteroid(world, enemySpriteFile);
			level.addGameObject(asteroid);
			totalEnemies++;
			enemySpawnIntervalCount++;
			lastSpawnTime = getSeconds();
		}
		
	}
	
}
