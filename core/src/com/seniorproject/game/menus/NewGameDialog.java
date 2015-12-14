package com.seniorproject.game.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.PlayerSave;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.helpers.TextFieldHelper;

public class NewGameDialog extends PopupDialog {

	boolean error = false;

	LabelHelper labelHelper;
		
	ButtonHelper startButtonHelper;

	Sprite errorBorder;
	LabelHelper messageLabelHelper;
	Label messageLabel;
	
	TextFieldHelper tfHelper;
	
	
	public NewGameDialog(Stage parentStage, final PlayMenu menu) {
		super(parentStage, menu.game);

		// Enter Name Label
		labelHelper = new LabelHelper("Enter your name:", 16, Color.WHITE, "opensans.ttf", menu.game); 
		Label enterNameLabel = labelHelper.getLabel();
		enterNameLabel.setAlignment(Align.bottomLeft);
		enterNameLabel.setPosition(dialogSprite.getX()+50, dialogSprite.getY()+dialogSprite.getHeight()-125);
		
		// Enter Name Textbox
		tfHelper = new TextFieldHelper((int) (dialogSprite.getWidth()-100), 35, game);
		
		final TextField nameText = tfHelper.getTextField();
		nameText.setPosition((stage.getWidth()/2)-(nameText.getWidth()/2), enterNameLabel.getY()-40);
		nameText.setMaxLength(16);
		
		
		// Start Button
		startButtonHelper = new ButtonHelper("menu/start-button.png", 204, 63, 0, 0, 0, 63, game); 
		startButtonHelper.setDisabledTexture("menu/start-button-disabled.png");
		
		ImageButton startButton = startButtonHelper.getButton();
		startButton.setPosition((stage.getWidth()/2)-(startButton.getWidth()/2), dialogSprite.getY()+50);
		startButton.setDisabled(true);
		
		stage.addActor(startButton);
		stage.addActor(enterNameLabel);
		stage.addActor(nameText);
		
		
		// Button Listener
		
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(!startButtonHelper.getButton().isDisabled()) {
					
					PlayerSave save = new PlayerSave();
					save.name = nameText.getText();
					save.score = 0;
					save.level = 1;
					save.lives = 3;
					save.rockets = 3;
					save.emp = 3;
					save.player_id = ShooterGame.PLAYER_ID;
					menu.game.db.savePlayer(save);
					menu.game.switchScreen(ShooterGame.GAME);
					
				}
			}
		});
		
		
	}

	@Override
	public void show() {
		super.show();
		tfHelper.getTextField().setText("");
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		if(!hidden && tfHelper != null && tfHelper.getTextField() != null && startButtonHelper != null) {
			
			if(tfHelper.getTextField().getText().isEmpty()) {
				startButtonHelper.getButton().setDisabled(true);
			}
			else {
				startButtonHelper.getButton().setDisabled(false);
			}
			
		}
	}
	
	
}
