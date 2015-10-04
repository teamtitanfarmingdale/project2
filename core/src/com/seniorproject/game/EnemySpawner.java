package com.seniorproject.game;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemySpawner extends GameActor {

	private int maxEnemies = 1;
	private int totalEnemies = 0;
	
	public EnemySpawner(World world) {
		super(world);
		collisionData.setActorType("EnemySpawner");
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(getStage() != null && totalEnemies < maxEnemies) {
			Enemy enemy = new Enemy(getWorld());
			this.getParent().getStage().addActor(enemy);
			totalEnemies++;
		}
		
	}
	
	public void createBody() {
		
	}
}
