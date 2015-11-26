package com.seniorproject.game.enemies;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.weapons.BaseBullet;

public class EnemyBullet extends BaseBullet {

	public EnemyBullet(Level l) {
		super(l);
		collisionData.setActorType("EnemyBullet");
	}

	public EnemyBullet(Level l, float x, float y) {
		super(l, x, y);
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
