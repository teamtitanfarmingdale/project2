package com.seniorproject.game;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemySpawner extends Actor {

	private int maxEnemies = 30;
	private int totalEnemies = 0;
	private String enemySpriteFile;
	private World world;
	
	private static final int ONE_BILLION = 1000000000;
	
	// Time between each enemy spawning
	private int spawnRate = 3;
	
	// Time the last enemy was spawned
	private long lastSpawnTime = 0;
	
	// How many enemies to spawn per spawnRate
	private int enemiesToSpawnEachInterval = 2;
	
	// Total enemies spawned this interval
	private int enemySpawnIntervalCount = 0;
	
	public EnemySpawner(World world, String enemySpriteFile) {
		this.world = world;
		this.enemySpriteFile = enemySpriteFile;
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
			Enemy enemy = new Enemy(world, enemySpriteFile);
			this.getParent().getStage().addActor(enemy);
			totalEnemies++;
			enemySpawnIntervalCount++;
			lastSpawnTime = getSeconds();
		}
		
	}

	public void setMaxEnemies(int maxEnemies) {
		this.maxEnemies = maxEnemies;
	}
	
	protected long getSeconds() {
		return System.nanoTime()/ONE_BILLION;
	}
	
	
}
