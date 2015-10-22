package com.seniorproject.game.hud;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LifeManager extends Actor {

	ArrayList<Sprite> lifeSprites;
	Texture lifeTexture;
	
	float startingX = 0;
	float startingY = 0;
	float lifeSpacing = 20;
	
	public LifeManager() {
		
		lifeSprites = new ArrayList<Sprite>();
		lifeTexture = new Texture(Gdx.files.internal("hud/life/life.png"));
		
	}
	
	public LifeManager(int lives) {
		this();
		for(int i = 0; i<lives; i++) {
			addLife();
		}
	}
	
	public void removeLife() {
		
		if(lifeSprites.size() > 0) {
			
			lifeSprites.remove(lifeSprites.size()-1);
			
		}
		
	}
	
	public void addLife() {
		
		Sprite tempSprite = new Sprite(lifeTexture);
		tempSprite.setPosition((startingX+(lifeSprites.size()*tempSprite.getWidth()+lifeSpacing)), startingY);
		
		lifeSprites.add(tempSprite);
		
	}
	
	public void draw(Batch batch, float alpha) {
		
		if(lifeSprites.size() > 0) {
			int counter = 0;
			
			for(Sprite s : lifeSprites) {
				s.setPosition((startingX+((counter*s.getWidth())+lifeSpacing)), startingY);
				s.draw(batch);
				counter++;
			}
		}
		
	}
	
	public void setStartingXPosition(float x) {
		startingX = x;
	}
	
	public void setStartingYPosition(float y) {
		startingY = y;
	}
	
	public int getLives() {
		return lifeSprites.size();
	}
	
}
