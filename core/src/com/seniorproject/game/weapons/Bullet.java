package com.seniorproject.game.weapons;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.levels.Level;


public class Bullet extends BaseBullet {
	
	int bulletSoundCount= 0;
	
	public Bullet(Level l) {
		super(l);
		collisionData.setActorType("Bullet");
	}

	public Bullet(Level l, float x, float y) {
		super(l, x, y);
		collisionData.setActorType("Bullet");
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0,  movementDistance);
		mba.setDuration(bulletSpeed);
		addAction(mba);
		
	}
	
	@Override
	public void createBody() {
		super.createBody();
		
		//Sound for bullets
		//wavSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
		if (bulletSoundCount == 0){
			//wavSound.play(ShooterGame.SFX_VOLUME);
			level.game.assetManager.playSound("sounds/laser.wav", 1f);
			bulletSoundCount++;
		}
	}
	
	@Override
	public void destroyBullet() {
		super.destroyBullet();
		//wavSound.dispose();
	}
}
