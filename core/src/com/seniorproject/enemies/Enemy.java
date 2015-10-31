package com.seniorproject.enemies;

import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends BaseEnemy {

	public Enemy(World world, String spriteFile) {
		super(world, spriteFile);
		
		collisionData.setActorType("Enemy");
		
		this.health = 30;
	}

}
