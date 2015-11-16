package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.helpers.TextFieldHelper;

public class SubmitScoreDialog extends PopupDialog {
	
	boolean scoreSubmitted = false;
	boolean error = false;
	int bonusPoints = 0;
	
	LabelHelper labelHelper;
		
	ButtonHelper saveButtonHelper;

	Sprite errorBorder;
	LabelHelper messageLabelHelper;
	Label messageLabel;
	
	TextFieldHelper tfHelper;
	
	public SubmitScoreDialog(Stage parentStage) {
		super(parentStage);
		
		// Enter Name Label
		labelHelper = new LabelHelper("Enter your name:", 16, Color.WHITE, "opensans.ttf"); 
		Label enterNameLabel = labelHelper.getLabel();
		enterNameLabel.setAlignment(Align.bottomLeft);
		enterNameLabel.setPosition(dialogSprite.getX()+50, dialogSprite.getY()+dialogSprite.getHeight()-125);
		
		// Enter Name Textbox
		tfHelper = new TextFieldHelper((int) (dialogSprite.getWidth()-100), 35);
		
		final TextField nameText = tfHelper.getTextField();
		nameText.setPosition((stage.getWidth()/2)-(nameText.getWidth()/2), enterNameLabel.getY()-40);
		
		/* Error Notifications */
		
		// Error Border
		Texture errorBorderTexture = new Texture(Gdx.files.internal("colors/pink.png"));
		errorBorderTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		errorBorder = new Sprite(errorBorderTexture);
		errorBorder.setSize(nameText.getWidth()+4, nameText.getHeight()+4);
		errorBorder.setPosition(nameText.getX()-2, nameText.getY()-2);
		
		// Error Label
		messageLabelHelper = new LabelHelper("You must enter a name!", 16, Color.WHITE, "opensans.ttf");
		messageLabel = messageLabelHelper.getLabel();
		messageLabel.setColor(1f,.5f, .5f, 1f);
		messageLabel.setAlignment(Align.bottom);
		messageLabel.setWidth(dialogSprite.getWidth());
		messageLabel.setPosition(dialogSprite.getX(), dialogSprite.getY()+dialogSprite.getHeight()-85);
		messageLabel.setVisible(false);
		

		// Save Button
		saveButtonHelper = new ButtonHelper("menu/submit-button.png", 204, 63, 0, 0, 0, 63);
		
		ImageButton saveButton = saveButtonHelper.getButton();
		saveButton.setPosition((stage.getWidth()/2)-(saveButton.getWidth()/2), dialogSprite.getY()+50);
		
		
		// Button Listeners		
		saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				error = false;
				messageLabel.setVisible(error);
				
				
				
				
				if(!scoreSubmitted && !nameText.getText().isEmpty()) {
				
					if(!saveButtonHelper.getButton().isDisabled()) {
						saveButtonHelper.getButton().setDisabled(true);
						HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
						httpPost.setUrl("http://spacetitans.tk/Score_Upload.php");
						httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
						httpPost.setContent("score="+(ShooterGame.PLAYER_SCORE+bonusPoints)+"&name="+nameText.getText()+"&level="+ShooterGame.CURRENT_LEVEL);
		
						Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
							
							String status;
							
							public void handleHttpResponse(HttpResponse httpResponse) {
								status = httpResponse.getResultAsString();
								System.out.println(status);
								saveButtonHelper.getButton().setDisabled(false);
								if(!status.equals("ok")) {
									status = "Unable to submit score at this time!";
									setErrorMessage(status);
									messageLabel.setVisible(true);
									error = true;
								}
								else {
									// Open browser?
									scoreSubmitted = true;
									setSuccessMessage("Successfully Submitted Score!");
									messageLabel.setVisible(true);
								
									Timer.schedule(new Task() {
	
										@Override
										public void run() {
											// TODO Auto-generated method stub
											hide();
										}
										
									}, 2f);
									
								
								}
								
								
							}
		
							public void failed(Throwable t) {
								status = "Unable to submit score at this time!";
								setErrorMessage(status);
								messageLabel.setVisible(true);
								error = true;
								saveButtonHelper.getButton().setDisabled(false);
							}
		
							@Override
							public void cancelled() {
								// TODO Auto-generated method stub
								saveButtonHelper.getButton().setDisabled(false);
							}
							
						});
					
					
					}

				
				}
				else if(scoreSubmitted) {
					setErrorMessage("You already submitted your score!");
					messageLabel.setVisible(true);
				}
				else {
					setErrorMessage("You must enter a name!");
					messageLabel.setVisible(true);
					error = true;
				}


				
			}
		});
		
		
		stage.addActor(messageLabel);
		stage.addActor(saveButton);
		stage.addActor(enterNameLabel);
		stage.addActor(nameText);
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		if(!hidden) {
			if(error) {
				errorBorder.draw(batch);
			}
		}
		
	}
	
	@Override
	public void hide() {
		super.hide();
		error = false;
		messageLabel.setVisible(false);
	}
	
	@Override
	public void show() {
		super.show();
		error = false;
		messageLabel.setVisible(false);
	}
	

	public boolean scoreSubmitted() {
		return scoreSubmitted;
	}
	
	public void setBonusPoints(int points) {
		bonusPoints = points;
	}
	
	private void setErrorMessage(String message) {
		messageLabel.setText(message);
		messageLabel.setColor(1f,.5f, .5f, 1f);
	}
	
	private void setSuccessMessage(String message) {
		messageLabel.setText(message);
		messageLabel.setColor(Color.GREEN);
	}
	
	@Override
	public void dispose() {
		fadedBGTexture.dispose();
		labelHelper.dispose();
		messageLabelHelper.dispose();
		cancelButtonHelper.dispose();
		saveButtonHelper.dispose();
		stage.dispose();
		errorBorder.getTexture().dispose();
	}
}
