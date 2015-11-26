package com.seniorproject.game.helpers;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.seniorproject.game.ShooterGame;

public class TextFieldHelper {

	TextField textField;

	public TextFieldHelper(int width, int height, ShooterGame game) {
		textField = new TextField("", game.defaultSkin);
		textField.setSize(width, height);
		
	}
	
	public TextField getTextField() {
		return textField;
	}
	
	
	public void dispose() {
		//skin.dispose();
	}
}
