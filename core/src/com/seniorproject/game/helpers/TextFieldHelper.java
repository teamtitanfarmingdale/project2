package com.seniorproject.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextFieldHelper {

	TextField textField;

	Skin skin;
	
	public TextFieldHelper(int width, int height) {
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		
		textField = new TextField("", skin);
		textField.setSize(width, height);
		
	}
	
	public TextField getTextField() {
		return textField;
	}
	
	
	public void dispose() {
		skin.dispose();
	}
}
