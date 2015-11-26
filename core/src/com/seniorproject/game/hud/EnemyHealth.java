package com.seniorproject.game.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.seniorproject.game.ShooterGame;

public class EnemyHealth extends BasicHealthBar {

	
	Texture healthBarTex;
	Texture wingTex;

	Texture backBarTex;
	
	Sprite healthBar;
	Sprite wings;
	Sprite backBar;
	
	
	public EnemyHealth(ShooterGame g) {
		super(g);
		
		healthBarTex = game.assetManager.getTexture("hud/enemyhealth/enemyhealth.png");
		wingTex = game.assetManager.getTexture("hud/enemyhealth/toprightwing.png");
		backBarTex = game.assetManager.getTexture("hud/enemyhealth/backbar.png");
		
		healthBar = new Sprite(healthBarTex);
		wings = new Sprite(wingTex);
		backBar = new Sprite(backBarTex);
		
		
		this.healthBarHeight = 20;
		
		setHealthBarSprite(healthBar);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		
		if (!isPositionSet() && getParent().getStage() != null
				&& positionYOffsetHeight != 0 && positionXOffsetWidth != 0) {
			
			parentWidth = getParent().getStage().getWidth();
			parentHeight = getParent().getStage().getHeight();
			
			wings.setPosition(parentWidth / 2 + (positionXOffsetWidth / 4),
					parentHeight - wings.getHeight()
							- (positionYOffsetHeight / 5));
			
			
			backBar.setPosition(parentWidth / 2
					+ (positionXOffsetWidth / 3.35f),
					parentHeight - backBar.getHeight()
							- (positionYOffsetHeight / 3.95f));
			
			healthBar.setPosition(parentWidth / 2
					+ (positionXOffsetWidth / 3.5f),
					parentHeight - healthBar.getHeight()
							- (positionYOffsetHeight / 4.33f));

			setPositionSet(true);

		}
		

		backBar.draw(batch);
		healthBar.draw(batch);
		wings.draw(batch);
	}
	
}
