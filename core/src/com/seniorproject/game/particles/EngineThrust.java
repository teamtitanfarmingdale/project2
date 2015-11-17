package com.seniorproject.game.particles;

import com.badlogic.gdx.graphics.g2d.Batch;

public class EngineThrust {
	
	InfiniteParticle leftThrust;
	InfiniteParticle rightThrust;
	
	
	
	public EngineThrust() {
		leftThrust = new InfiniteParticle("particles/engine-thrust.particle");
		rightThrust = new InfiniteParticle("particles/engine-thrust.particle");
	}
	
	public void draw(Batch batch) {
		leftThrust.draw(batch);
		rightThrust.draw(batch);
	}
	
	public void stop() {
		leftThrust.stop();
		rightThrust.stop();
	}
	
	public void reset() {
		leftThrust.reset();
		rightThrust.reset();
	}
	
	public void start(float x, float y) {
		leftThrust.start(x, y);
		rightThrust.start(x+20, y);
	}
	
	public void dispose() {
		leftThrust.dispose();
		rightThrust.dispose();
	}
	
}
