package com.seniorproject.game.enemies;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.seniorproject.game.levels.Level;

public class AsteroidSpawner extends Spawner {

	ArrayList<Asteroid> asteroidList;
	ArrayList<Asteroid> asteroidDeadList;
	
	
	public AsteroidSpawner(World world, String enemyAsteroidFile) {
		super(world, enemyAsteroidFile);
		
		maxEnemies = 100;
		totalEnemies = 0;
		spawnRate = 4;
		lastSpawnTime = 2;
		enemiesToSpawnEachInterval = 4;
		enemySpawnIntervalCount = 0;
		
		asteroidList = new ArrayList<Asteroid>();
		asteroidDeadList = new ArrayList<Asteroid>();
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
			asteroidList.add(asteroid);
			totalEnemies++;
			enemySpawnIntervalCount++;
			lastSpawnTime = getSeconds();
		}
		
		
		// Check list for dead asteroids
		if(asteroidList.size() > 0) {
			for(Asteroid a : asteroidList) {
				if(a.isDead()) {
					asteroidDeadList.add(a);
				}
			}
			
			if(asteroidDeadList.size() > 0) {
				for(Asteroid a : asteroidDeadList) {
					asteroidList.remove(a);
				}
				
				asteroidDeadList.clear();
			}
			
		}
		
	}
	
	public void stop() {
		totalEnemies = maxEnemies;
		
		for(Asteroid a : asteroidList) {
			a.silentDeath(true);
		}
		
	}
	
}
