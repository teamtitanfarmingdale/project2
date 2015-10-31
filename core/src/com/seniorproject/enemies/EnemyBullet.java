package com.seniorproject.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.BaseBullet;

public class EnemyBullet extends BaseBullet {

	public EnemyBullet(World world) {
		super(world);
		collisionData.setActorType("EnemyBullet");
	}

	public EnemyBullet(World world, float x, float y) {
		super(world, x, y);
		collisionData.setActorType("EnemyBullet");
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0,  -movementDistance);
		mba.setDuration(bulletSpeed);
		addAction(mba);
		
	}
	
}
