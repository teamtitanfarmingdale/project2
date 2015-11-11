package com.seniorproject.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontHelper {

	private final static String FONT_FILE = "play.ttf";
	private BitmapFont font;
	
	
	public FontHelper(int fontSize) {
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_FILE));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = fontSize;

		font = generator.generateFont(parameter);
		generator.dispose();
		
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
}
