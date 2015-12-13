package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.helpers.TextFieldHelper;

public class LoginDialog extends PopupDialog {

	protected Label dialogTitle;
	protected Label emailLabel;
	protected Label passwordLabel;
	protected Label signUpMessageLabel;
	protected Label signUpLabel;
	protected Label messageLabel;
	
	protected TextField emailText;
	protected TextField passwordText;
	protected ImageButton loginButton;
	
	protected VictoryScreen victoryScreen;
	
	public LoginDialog(Stage parentStage, ShooterGame g) {
		super(parentStage, g);
		
		int componentOffset = 35;
		
		// Labels
		LabelHelper emailLabelHelper = new LabelHelper("E-mail:", 14, Color.WHITE, "opensans.ttf", g);
		LabelHelper passwordLabelHelper = new LabelHelper("Password:", 14, Color.WHITE, "opensans.ttf", g);
		LabelHelper titleLabelHelper = new LabelHelper("Login", 25, Color.WHITE, "opensans.ttf", g);
		LabelHelper signUpMessageLabelHelper = new LabelHelper("- Don't have an account?", 14, Color.WHITE, "opensans.ttf", g);
		LabelHelper signUpLabelHelper = new LabelHelper("Sign Up!", 14, Color.CYAN, "opensans.ttf", g);
		LabelHelper messageLabelHelper = new LabelHelper("", 14, Color.WHITE, "opensans.ttf", g);
		
		
		dialogTitle = titleLabelHelper.getLabel();
		emailLabel = emailLabelHelper.getLabel();
		passwordLabel = passwordLabelHelper.getLabel();
		signUpMessageLabel = signUpMessageLabelHelper.getLabel();
		signUpLabel = signUpLabelHelper.getLabel();
		
		messageLabel = messageLabelHelper.getLabel();
		messageLabel.setWidth(dialogSprite.getWidth());
		messageLabel.setAlignment(Align.center);
		messageLabel.setColor(1f,.5f, .5f, 1f);
		
		// Text Fields
		TextFieldHelper emailTextHelper = new TextFieldHelper((int) (dialogSprite.getWidth()-100), 35, g);
		TextFieldHelper passwordTextHelper = new TextFieldHelper((int) (dialogSprite.getWidth()-100), 35, g);
		
		emailText = emailTextHelper.getTextField();
		
		passwordText = passwordTextHelper.getTextField();
		passwordText.setPasswordMode(true);
		passwordText.setPasswordCharacter('*');
		
		// Button
		ButtonHelper loginButtonHelper = new ButtonHelper("menu/login-button-small.png", 162, 50, 0, 0, 0, 50, game);
		loginButton = loginButtonHelper.getButton();
		
		
		// Positioning
		dialogTitle.setPosition(dialogSprite.getX()+30, dialogSprite.getY()+dialogSprite.getHeight()-70);
		signUpMessageLabel.setPosition(dialogTitle.getX()+dialogTitle.getWidth()+10, dialogTitle.getY()+(signUpMessageLabel.getHeight()/2)-5);
		signUpLabel.setPosition(signUpMessageLabel.getX()+signUpMessageLabel.getWidth()+10, signUpMessageLabel.getY());
		
		messageLabel.setPosition(dialogSprite.getX(), signUpLabel.getY()-signUpLabel.getHeight()-5);
		
		emailLabel.setPosition(dialogTitle.getX()+22, dialogTitle.getY()-55);
		emailText.setPosition((stage.getWidth()/2)-(emailText.getWidth()/2), emailLabel.getY()-componentOffset);
		
		passwordLabel.setPosition(emailLabel.getX(), emailText.getY()-componentOffset);
		passwordText.setPosition(emailText.getX(), passwordLabel.getY()-componentOffset);
		
		loginButton.setPosition((stage.getWidth()/2)-(loginButton.getWidth()/2), passwordText.getY()-loginButton.getHeight()-10);
		
		// BUTTON EVENTS
		signUpLabel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://spacetitans.tk/log-in.php");
			}
		});
		
		loginButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				LoginDialog.this.login(emailText.getText(), passwordText.getText());
			}
		});
		
		
		
		hideMessage();
		
		stage.addActor(dialogTitle);
		stage.addActor(emailLabel);
		stage.addActor(passwordLabel);
		stage.addActor(signUpMessageLabel);
		stage.addActor(signUpLabel);
		stage.addActor(emailText);
		stage.addActor(passwordText);
		stage.addActor(loginButton);
		stage.addActor(messageLabel);
	}
	
	
	@Override
	public void initDialogBG() {
			
		dialogBGPath = "menu/dialog-big-bg.png";
		
		// Dialog
		Texture dialogTexture = game.assetManager.getTexture(dialogBGPath);
		dialogSprite = new Sprite(dialogTexture);
		
		dialogSprite.setSize(518, 318);
		dialogSprite.setPosition((stage.getWidth()/2)-(dialogSprite.getWidth()/2), (stage.getHeight()/2)-(dialogSprite.getHeight()/2));
		
		
	}
	
	
	@Override
	public void initCancelButton() {
		// Cancel Button
		cancelButtonHelper = new ButtonHelper("menu/save-score-popup/cancel-button.png", 71, 71, 0, 0, 0, 71, game);
		cancelButton = cancelButtonHelper.getButton();
		
		cancelButton.setPosition((dialogSprite.getX()+dialogSprite.getWidth()-(cancelButton.getWidth()/2)-8), dialogSprite.getY()+dialogSprite.getHeight()-cancelButton.getHeight()+28);
	}
	
	@Override
	public void show() {
		super.show();
		hideMessage();
	}

	public void showSuccessMessage() {
		messageLabel.setColor(Color.LIME);
		messageLabel.setText("Login Successful!");
		messageLabel.setVisible(true);
	}
	
	public void showErrorMessage(String message) {
		messageLabel.setColor(1f,.5f, .5f, 1f);
		messageLabel.setText(message);
		messageLabel.setVisible(true);
	}
	
	public void showErrorMessage() {
		showErrorMessage("Invalid Username/Password");
	}
	
	public void hideMessage() {
		messageLabel.setVisible(false);
	}
	
	
	public void login(String username, String password) {
		
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://spacetitans.tk/ingame-login.php");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setContent("username="+username+"&password="+password);

		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
			
			String status;
			
			public void handleHttpResponse(HttpResponse httpResponse) {
				status = httpResponse.getResultAsString();
				Json jsonResult = new Json();
				
				
				ObjectMap map = jsonResult.fromJson(ObjectMap.class, status);
				boolean result = (boolean) map.get("result");
				String tempPlayerID = (String) map.get("player_id");
				
				int playerID = 0;
				
				try {
					playerID = Integer.valueOf(tempPlayerID);
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
				

				
				if(result) {
					LoginDialog.this.showSuccessMessage();
					ShooterGame.PLAYER_ID = playerID;
					System.out.println(playerID);
					
					Timer.schedule(new Task() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							LoginDialog.this.hide();
							
							if(victoryScreen != null) {
								victoryScreen.submitScoreDialog.show();
							}
							
						}
						
					}, 2f);
					
				}
				else {
					LoginDialog.this.showErrorMessage();
				}
				
				System.out.println(status);
				
			}

			public void failed(Throwable t) {
				System.out.println("FAILED");
				LoginDialog.this.showErrorMessage();
			}

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				status = "cancelled";
				System.out.println(status);
			}
			
		});
		
	}
	
	public void setVictoryScreen(VictoryScreen screen) {
		victoryScreen = screen;
	}
	
}
