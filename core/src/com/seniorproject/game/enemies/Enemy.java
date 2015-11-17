package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.hud.EnemyHealth;
import com.seniorproject.game.particles.FireExplosion;

public class Enemy extends BaseEnemy {

	
	boolean healthBarLowered = false;
	
	public Enemy(World world, String spriteFile) {
		super(world, spriteFile);
		
		collisionData.setActorType("Enemy");
		
		this.health = 30;
		
		explosion = new FireExplosion();
		
	}

	
	@Override
	public void setDead(boolean dead) {
		super.setDead(dead);
		
		
		if(this.dead && !healthBarLowered) {
			healthBarLowered = true;
			float healthDrop = 0;
			try {
				healthDrop = (level.enemyHealthBar.MAX_HEALTH/level.enemySpawner.getMaxEnemies());
				//System.out.println(healthDrop);
				//System.out.println(level.enemySpawner.getMaxEnemies());
			}
			catch(Exception e) { }
			
			level.enemyHealthBar.lowerHealth(healthDrop);
		}
	}
	
	@Override
	public void lowerHealth(int damage) {
		super.lowerHealth(damage);
		getLevel().addAction(GeneralHelper.shakeSprite(getSprite()));
	}
	
}
