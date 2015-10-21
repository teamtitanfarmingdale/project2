package com.seniorproject.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Armor extends BasicHealthBar {
	
	Texture backBarTex = new Texture(Gdx.files.internal("hud/armor/backbar.png"));
	Texture armorBarTex = new Texture(Gdx.files.internal("hud/armor/armorbar.png"));
	Texture wingTex = new Texture(Gdx.files.internal("hud/armor/toprightwing.png"));
	
	Sprite backbar = new Sprite (backBarTex);
	Sprite armorbar = new Sprite (armorBarTex);
	Sprite wings = new Sprite (wingTex);
	
	//sentinel value to keep the position from reassigning
	int i =0;
	
	public Armor() {
		setHealthBarSprite(armorbar);
	}
	
	public void draw(Batch batch, float alpha){
		super.draw(batch, alpha);
		
		if (getParent().getStage() != null && i==0 && positionYOffsetHeight != 0 && positionXOffsetWidth != 0) {
			
			/*
			backbar.setPosition(getParent().getStage().getWidth()/2 + (backbar.getWidth()/19) , getParent().getStage().getHeight() - backbar.getHeight() - (wings.getHeight()/3));
			armorbar.setPosition(getParent().getStage().getWidth()/2 + (armorbar.getWidth()/19) , getParent().getStage().getHeight() - armorbar.getHeight() - (wings.getHeight()/3));
			wings.setPosition(getParent().getStage().getWidth()/2 , getParent().getStage().getHeight() - wings.getHeight());
			
			*/
			parentWidth = getParent().getStage().getWidth();
			parentHeight = getParent().getStage().getHeight();
			
			
			backbar.setPosition(parentWidth/2 + (positionXOffsetWidth/2.5f), parentHeight - backbar.getHeight() - (positionYOffsetHeight/3.3f));
			armorbar.setPosition(parentWidth/2 + (positionXOffsetWidth/2.5f), parentHeight - armorbar.getHeight() - (positionYOffsetHeight/3.3f));
			wings.setPosition(parentWidth/2 + (positionXOffsetWidth/4), parentHeight - wings.getHeight() - (positionYOffsetHeight/5));
			
			setPositionSet(true);
			i++;//makes sure that it does not reassign the positions in the following iterations
		}
		
		backbar.draw(batch);
		armorbar.draw(batch);
		wings.draw(batch);
	}
	
	
}
