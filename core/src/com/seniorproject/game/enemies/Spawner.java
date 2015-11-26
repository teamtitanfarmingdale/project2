package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.seniorproject.game.levels.Level;

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
	
	public Spawner(Level l, String enemySpriteFile) {
		this.level = l;
		this.world = l.world;
		this.enemySpriteFile = enemySpriteFile;
	}
	
	public void setMaxEnemies(int maxEnemies) {
		this.maxEnemies = maxEnemies;
	}
	
	public static long getSeconds() {
		return System.nanoTime()/ONE_BILLION;
	}
	
	public int getMaxEnemies() {
		return maxEnemies;
	}
	
	
}
