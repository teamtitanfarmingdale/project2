package com.seniorproject.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseExplosion extends Actor implements ParticleInterface {
	
	ParticleEffect explosion;
	boolean explosionStarted = false;
	
	public String particleFile;
	
	
	public BaseExplosion(String explosionFile) {
		
		particleFile = explosionFile;
		explosion = new ParticleEffect();
		explosion.load(Gdx.files.internal(explosionFile), Gdx.files.internal(""));
		
	}
	
	@Override
	public void draw(Batch batch, float delta) {
		super.draw(batch, delta);
		
		if(explosion != null && explosionStarted) {
			explosion.update(Gdx.graphics.getDeltaTime());
			explosion.draw(batch);
			
			if(explosion.isComplete()) {
				explosionStarted = false;
				explosion.reset();
			}
			
		}
		
	}
	
	public void start(float x, float y) {
		
		for(ParticleEmitter pe : explosion.getEmitters()) {
			pe.setPosition(x,  y);
		}
		
		explosionStarted = true;
		explosion.start();
	}
	
	public void stop() {
		explosionStarted = false;
	}
	
	public boolean started() {
		return explosionStarted;
	}
}
