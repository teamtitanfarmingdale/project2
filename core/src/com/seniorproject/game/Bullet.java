package com.seniorproject.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.audio.Sound;


public class Bullet extends BaseBullet {
	
	
	int bulletSoundCount= 0;
	
	public Bullet(World world) {
		super(world);
		collisionData.setActorType("Bullet");
	}

	public Bullet(World world, float x, float y) {
		super(world, x, y);
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
		Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
		if (bulletSoundCount == 0){
			wavSound.play(ShooterGame.SFX_VOLUME);
			bulletSoundCount++;
		}
	}
	
}
