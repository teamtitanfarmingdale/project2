package com.seniorproject.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Health extends BasicHealthBar {

	private static final float MAX_HEALTH = 100;
	private static final int DANGER_HEALTH_FRAMES = 22; 
	private static final int HEALTH_WARNING_AMOUNT = 30;
	
	// Animation Variables
	private TextureRegion[] animationFrames;
	private Animation backbarAnimation;
	private float elapsedTime = 0;
	
	// Health Warning Info
	private boolean warningMode = false;

	
	// Sprite and Texture Variables
	private Texture backBarTexture;
	private Texture backBarDangerTexture;
	private Sprite backBarSprite;
	private Sprite backBarWarningSprite;
	
	private Texture topLeftWingTexture;
	private Sprite topLeftWingSprite;
	
	private Texture healthBarTexture;
	
	private Sprite healthBarSprite;
	
	private Texture bottomLeftWingTexture;
	private Sprite bottomLeftWingSprite;
	
	
	// Positioning Variables
	float halfParentWidth;
	
	float topLeftWingX;
	float topLeftWingY;
	
	float backBarX;
	float backBarY;
	
	float healthBarX;
	float healthBarY;
	
	float bottomLeftWingX;
	float bottomLeftWingY;

	
	public LifeManager lifeManager;
	
	public Health() {
		
		
		lifeManager = new LifeManager(3);

		
		backBarTexture = new Texture(Gdx.files.internal("hud/health/backbar.png"));
		backBarDangerTexture = new Texture(Gdx.files.internal("hud/health/backbar-danger.png"));
		backBarSprite = new Sprite(backBarTexture);
		backBarWarningSprite = new Sprite(backBarTexture);

		healthBarTexture = new Texture(Gdx.files.internal("hud/health/healthbar.png"));
		healthBarSprite = new Sprite(healthBarTexture);
		
		setHealthBarSprite(healthBarSprite);
		
		topLeftWingTexture = new Texture (Gdx.files.internal("hud/health/topleftwing.png"));
		topLeftWingSprite = new Sprite(topLeftWingTexture);
		
		bottomLeftWingTexture = new Texture (Gdx.files.internal("hud/life/bottomleftwing.png"));
		bottomLeftWingSprite = new Sprite(bottomLeftWingTexture);

		// Initial Positions
		backBarSprite.setPosition(50, 688);
		healthBarSprite.setPosition(50, 688);
		topLeftWingSprite.setPosition(10, 670);
		bottomLeftWingSprite.setPosition(64, 640);
		
		// Set up animated health bar when health goes below 20
		animationFrames = new TextureRegion[DANGER_HEALTH_FRAMES];
		
		TextureRegion[][] tmpFrames = TextureRegion.split(backBarDangerTexture, 454, 15);

		int index = 0;
		for(int i = 0; i<2; i++) {
			for(int j = 0; j<11; j++) {
				animationFrames[index++] = tmpFrames[i][j];
			}
		}
		
		backbarAnimation = new Animation(1f/22f, animationFrames);
		backbarAnimation.setPlayMode(Animation.PlayMode.LOOP);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		
		
		if(!isPositionSet() && getParent().getStage() != null && positionXOffsetWidth != 0 && positionYOffsetHeight != 0) {
			
			
			// Reposition when health is decreased
			setPositionSet(true);
			halfParentWidth = (getParent().getStage().getWidth()/2);
			parentHeight = getParent().getStage().getHeight();
			

			topLeftWingX = halfParentWidth-(topLeftWingSprite.getWidth()+(positionXOffsetWidth/4));
			topLeftWingY = (parentHeight-topLeftWingSprite.getHeight())-(positionYOffsetHeight/5);
			
			backBarX = halfParentWidth-(backBarSprite.getWidth()+(positionXOffsetWidth/2.2f));
			backBarY = (parentHeight-backBarSprite.getHeight())-(positionYOffsetHeight/3.3f);
			
			bottomLeftWingX = halfParentWidth-(bottomLeftWingSprite.getWidth()-(positionXOffsetWidth/10.5f));
			bottomLeftWingY = topLeftWingY-bottomLeftWingSprite.getHeight()+(positionYOffsetHeight/8.7f);
						
			healthBarX = halfParentWidth-(healthBarSprite.getWidth()+(positionXOffsetWidth/2.5f));
			healthBarY = (parentHeight-healthBarSprite.getHeight())-(positionYOffsetHeight/3.3f);
			
			healthBarSprite.setPosition(healthBarX, healthBarY);
			topLeftWingSprite.setPosition(topLeftWingX, topLeftWingY);
			backBarSprite.setPosition(backBarX, backBarY);
			backBarWarningSprite.setPosition(backBarX, backBarY);
			bottomLeftWingSprite.setPosition(bottomLeftWingX, bottomLeftWingY);
			
			
			lifeManager.setStartingXPosition(bottomLeftWingX+(bottomLeftWingSprite.getWidth()/4.5f));
			lifeManager.setStartingYPosition(bottomLeftWingY+(bottomLeftWingSprite.getHeight()/5f));
			
			
			// Turn on health warning
			if(!warningMode && totalHealth < HEALTH_WARNING_AMOUNT) {
				warningMode = true;
			}
			else if(warningMode && totalHealth >= HEALTH_WARNING_AMOUNT) {
				warningMode = false;
				backBarSprite.setTexture(backBarTexture);
			}
			
		}
		
		if(warningMode) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			backBarWarningSprite.setRegion(backbarAnimation.getKeyFrame(elapsedTime, true));
			backBarWarningSprite.draw(batch);
		}
		else {
			backBarSprite.draw(batch);
		}
		
		healthBarSprite.draw(batch);
		topLeftWingSprite.draw(batch);
		bottomLeftWingSprite.draw(batch);
		lifeManager.draw(batch, alpha);

	}
	
}
