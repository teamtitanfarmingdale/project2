package com.seniorproject.game.powerups;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.Asset;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.InfiniteParticle;
import com.seniorproject.game.particles.ParticleInterface;


public abstract class BasePowerup extends GameActor {

	protected TextureRegion textureRegion;
	protected float movementDistance = 3f;
	protected float movementSpeed = .01f;
	protected int spriteHeight = 50;
	protected int spriteWidth = 76;
	protected int actualHeight = 0;
	
	protected InfiniteParticle particleEffect;
	protected Asset<ParticleInterface> particleInterface;
	
	public BasePowerup(Level l) {
		super(l);
		collisionData.setActorType("PowerUp");
		movementDistance = (float) (Math.random() * 2f)+1f;
		movementSpeed = (float) (Math.random() * .01f)+.01f;
		
		setupSprite("powerups/powerup-sprite.png");

		particleInterface = level.game.assetManager.getParticle("particles/powerup.particle", "InfiniteParticle");
		particleEffect = (InfiniteParticle) particleInterface.asset; //new InfiniteParticle("particles/powerup.particle");
		particleEffect.setStartTime(-1);
		particleEffect.getParticleEffect().reset();
	}
	
	
	@Override
	public void setupSprite(String spriteFileName) {
		texture = level.game.assetManager.getTexture("powerups/powerup-sprite.png");
		textureRegion = new TextureRegion(texture);
		sprite = new Sprite(textureRegion);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0f, -movementDistance);
		mba.setDuration(movementSpeed);
		addAction(mba);

		if(getY()+getHeight() < 0) {
			setDead(true);
			this.remove();
			this.dispose();
		}
		
	}
	
	@Override
	public void draw(Batch batch, float delta) {
		
		if(!isDead()) {
			particleEffect.start(getX()+(getSprite().getWidth()/2), getY()+(getSprite().getHeight()/2));
			particleEffect.draw(batch);
		}
		getSprite().draw(batch);
	}
	
	@Override
	public void setDead(boolean dead) {
		
		System.out.println("Killed Particle");
		if(!isDead() && dead) {
			level.game.assetManager.releaseParticle(particleInterface, 0f);
			//particleEffect.reset();
		}
		
		this.dead = dead;
	}
	
	@Override
	protected void positionChanged() {
		getSprite().setPosition(getX(), getY());
		//System.out.println("position changed! - "+getX()+", "+getY());
		createBody();
		super.positionChanged();
	}
	
	@Override
	public void setStage(Stage stage) {
		super.setStage(stage);
		
		if(stage != null) {
			
			Double randomNumber = (Math.random()*(stage.getWidth()-getSprite().getWidth()));
			int randomX = randomNumber.intValue();
			
			// Spawns the enemy at a random X location at the top of the screen
			setBounds(randomX, (stage.getHeight()-(getSprite().getHeight()-100)), getSprite().getWidth(), getSprite().getHeight());

		}
		
	}
	
	public void setParticleColor(float[] rgb) {
		particleEffect.getParticleEffect().findEmitter("stars").getTint().setColors(rgb);
	}
	
	public abstract void applyPowerup();

}
