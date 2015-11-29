package com.seniorproject.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;

public class InfiniteParticle implements ParticleInterface {
	
	ParticleEffect particleEffect;
	boolean effectStarted = false;
	
	private int startTime = 0;
	
	public String particleFile;
	
	
	public InfiniteParticle(String particleFile) {
		
		this.particleFile = particleFile;
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal(particleFile), Gdx.files.internal(""));
		
	}
	
	public ParticleEffect getParticleEffect() {
		return particleEffect;
	}
	

	public void draw(Batch batch) {

		if(particleEffect != null) {
			
			particleEffect.update(Gdx.graphics.getDeltaTime());
			
			if(effectStarted) {
				
				if(startTime >= 0) {
					for(ParticleEmitter pe : particleEffect.getEmitters()) {
						pe.durationTimer = startTime;
					}
				}
				
				
				particleEffect.draw(batch);
			
				
				if(particleEffect.isComplete()) {
					//particleEffect.reset();
				}
			}
		}
		
	}
	
	public void start(float x, float y) {
		this.setParticlePosition(x, y);
		effectStarted = true;
		particleEffect.start();
	}
	
	public void stop() {
		effectStarted = false;
	}
	
	public void reset() {
		for(ParticleEmitter pe : particleEffect.getEmitters()) {
			//pe.durationTimer = 500;
			//pe.reset();
			particleEffect.update(Gdx.graphics.getDeltaTime()+500);
		}
		//particleEffect.reset();
	}
	
	public void setParticlePosition(float x, float y) {
		for(ParticleEmitter pe : particleEffect.getEmitters()) {
			pe.setPosition(x,  y);
		}
	}
	
	public void dispose() {
		particleEffect.dispose();
	}
	
	public void setStartTime(int time) {
		startTime = time;
	}
}
