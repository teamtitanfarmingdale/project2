package com.seniorproject.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class TextFieldHelper {

	TextField textField;
	Sprite whiteBG;
	Sprite blackBG;
	
	
	public TextFieldHelper(int width, int height, BitmapFont font) {
		/*
		// Background Color
		Texture whiteBGTexture = new Texture(Gdx.files.internal("whitebg.png"));
		whiteBGTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		whiteBG = new Sprite(whiteBGTexture);
		whiteBG.setSize(width,  height);
		
		SpriteDrawable whiteBGDrawable = new SpriteDrawable(whiteBG);
		
		
		// Cursor
		Texture blackBGTexture = new Texture(Gdx.files.internal("blackbg.png"));
		blackBGTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		blackBG = new Sprite(blackBGTexture);
		blackBG.setSize(2, height-10);
		SpriteDrawable blackBGDrawable = new SpriteDrawable(blackBG);
		
		TextFieldStyle tfStyle = new TextFieldStyle();
		tfStyle.font = font;
		tfStyle.cursor = blackBGDrawable;
		tfStyle.background = whiteBGDrawable;
		tfStyle.fontColor = Color.BLACK;
		*/
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		
		textField = new TextField("", skin);
		textField.setSize(width, height);
		
	}
	
	public TextField getTextField() {
		return textField;
	}
	
}
