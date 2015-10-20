package com.seniorproject.game;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Spawner extends Actor {

	protected int maxEnemies;
	protected int totalEnemies;
	protected String enemySpriteFile;
	protected World world;
	protected Level level;
	
	protected static final int ONE_BILLION = 1000000000;
	
	// Time between each enemy spawning
	protected int spawnRate;
	
	// Time the last enemy was spawned
	protected long lastSpawnTime;
	
	// How many enemies to spawn per spawnRate
	protected int enemiesToSpawnEachInterval;
	
	// Total enemies spawned this interval
	protected int enemySpawnIntervalCount;
	
	public Spawner(World world, String enemySpriteFile) {
		this.world = world;
		this.enemySpriteFile = enemySpriteFile;
	}
	
	public void setMaxEnemies(int maxEnemies) {
		this.maxEnemies = maxEnemies;
	}
	
	protected long getSeconds() {
		return System.nanoTime()/ONE_BILLION;
	}
	
	
}
