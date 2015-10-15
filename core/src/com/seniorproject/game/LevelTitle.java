package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LevelTitle {

	private final static String FONT_FILE = "play.ttf";
	
	
	/* 	Estimate for converting points into pixels:
	 	The label takes in a point value for the font size so to determine the height of the label
	 	we need to convert points into pixels.
	
	 	example: fontSizeInPoints*1.35 = fontSizeInPixels
	 */
	private final static double POINTS_TO_PIXELS_MULTIPLIER = 1.35;
	
	private Skin skin;
	private Label label;
	private LabelStyle labelStyle;
	private BitmapFont font;
	
	
	public LevelTitle(String title, int fontSize) {
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		setupFont(fontSize);
		
		skin.add("default-font",  font, BitmapFont.class);
		
		label = new Label(title, skin);
		
		labelStyle = new LabelStyle(skin.getFont("default-font"), Color.WHITE);
		label.setStyle(labelStyle);
		
		label.setHeight((float) (fontSize*POINTS_TO_PIXELS_MULTIPLIER));
		
	}
	
	public LevelTitle() {
		this("", 12);
	}
	
	public void setColor(Color color) {
		labelStyle.fontColor = color;
	}
	
	
	public Label getLabel() {
		return label;
	}
	
	public void setTitle(String title) {
		label.setText(title);
	}
	
	public void setPosition(Level level) {
		// Set to top center
		
		float x = (level.getWidth()/2) - (label.getWidth()/2);
		float y = level.getHeight() - label.getHeight();
		label.setPosition(x, y);
		
		//label.setWidth(level.getWidth());
		
	}
	
	private void setupFont(int fontSize) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_FILE));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = fontSize;

		font = generator.generateFont(parameter);
		generator.dispose();
	}
	
}
