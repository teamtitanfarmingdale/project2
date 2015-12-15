package com.seniorproject.game.menus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.seniorproject.game.ShooterGame;

public class ControlsDialog extends PopupDialog {

	
	protected Image controlsImage;
	
	public ControlsDialog(Stage parentStage, ShooterGame g) {
		super(parentStage, g);
		
		
		Texture controlsTexture = game.assetManager.getTexture("controls.png");
		controlsImage = new Image(controlsTexture);
		controlsImage.setSize(440,190);
		
		float controlImageX = dialogSprite.getX()+((dialogSprite.getWidth()-controlsImage.getWidth())/2);
		float controlImageY = dialogSprite.getY()+((dialogSprite.getHeight()-controlsImage.getHeight())/2);
		controlsImage.setPosition(controlImageX, controlImageY);
		
		stage.addActor(controlsImage);
		
	}

}
