package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SaveSlot extends Actor {

	
	Texture temp;
	
	Sprite tempSprite;
	
	
	public SaveSlot(String textureFile) {
		temp = new Texture(Gdx.files.internal(textureFile));
		temp.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		tempSprite = new Sprite(temp);
		tempSprite.setSize(300, 50);
		this.setSize(300, 50);
	}
	
	
	public SaveSlot() {
		this("colors/red.png");
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		
		tempSprite.setPosition(this.getX(), this.getY());
		tempSprite.draw(batch);
		
	}
	
}
