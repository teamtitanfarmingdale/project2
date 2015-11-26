package com.seniorproject.game.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.seniorproject.game.ShooterGame;

public class LabelHelper {
	
	private final static String FONT_FILE = "play.ttf";
	private final static double POINTS_TO_PIXELS_MULTIPLIER = 2;
	
	private Skin skin;
	private Label label;
	private LabelStyle labelStyle;
	private BitmapFont font;
	private ShooterGame game;
	
	private Color color;
	
	public LabelHelper(String title, int fontSize, Color color, String fontFile, ShooterGame g) {
		
		// Skin is needed for the label
		skin = new Skin();
		
		game = g;
		
		this.color = color;
		
		setupFont(fontSize, fontFile, color);
		
		labelStyle = new LabelStyle(font, color);
		
		skin.add("default-font",  font);
		skin.add("default", labelStyle);
		
		label = new Label(title, skin);
		label.setHeight((float) (fontSize*POINTS_TO_PIXELS_MULTIPLIER));
	}
	
	public LabelHelper(String title, int fontSize, Color color, ShooterGame g) {
		this(title, fontSize, color, FONT_FILE, g);
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
	
	public BitmapFont getFont() {
		return font;
	}
	
	
	private void setupFont(int fontSize, String fontFile, Color color) {
		// Creates a Bitmap font from the .ttf font file
		/*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = fontSize;

		font = generator.generateFont(parameter);
		generator.dispose();*/
		
		font = game.assetManager.getFont(fontFile, fontSize, color);
	}
	
	public void setupFont(int fontSize) {
		this.setupFont(fontSize, FONT_FILE, color);
	}

	public void dispose() {
		//skin.dispose();
	}
	
}
