package com.seniorproject.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EnemyHealth extends BasicHealthBar {

	
	Texture healthBarTex = new Texture(
			Gdx.files.internal("hud/enemyhealth/enemyhealth.png"));
	Texture wingTex = new Texture(
			Gdx.files.internal("hud/enemyhealth/toprightwing.png"));

	Texture backBarTex = new Texture(Gdx.files.internal("hud/enemyhealth/backbar.png"));
	
	Sprite healthBar = new Sprite(healthBarTex);
	Sprite wings = new Sprite(wingTex);
	Sprite backBar = new Sprite(backBarTex);
	
	
	public EnemyHealth() {
		
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
