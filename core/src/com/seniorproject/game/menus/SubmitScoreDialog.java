package com.seniorproject.game.menus;

import javax.swing.JOptionPane;

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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.helpers.TextFieldHelper;

public class SubmitScoreDialog {
	
	boolean hidden = true;
	boolean error = false;
	boolean scoreSubmitted = false;
	int bonusPoints = 0;
	
	Stage stage;
	Stage parentStage;
	
	LabelHelper labelHelper;
	
	Sprite dialogSprite;
	ButtonHelper cancelButtonHelper;
	ImageButton cancelButton;
	
	ButtonHelper saveButtonHelper;
	
	Texture fadedBGTexture;
	Sprite fadedBG;
	
	Sprite errorBorder;
	LabelHelper messageLabelHelper;
	Label messageLabel;
	
	public SubmitScoreDialog(Stage parentStage) {
		
		this.parentStage = parentStage;
		
		stage = new Stage();
		
		// FADED BACKGROUND
		fadedBGTexture = new Texture(Gdx.files.internal("faded-black-bg.png"));
		fadedBGTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		fadedBG = new Sprite(fadedBGTexture);
		fadedBG.setSize(ShooterGame.GAME_WIDTH, ShooterGame.GAME_HEIGHT);
		
		
		// Dialog
		Texture dialogTexture = new Texture(Gdx.files.internal("menu/save-score-popup/bg.png"));
		dialogSprite = new Sprite(dialogTexture);
		
		dialogSprite.setSize(536, 286);
		dialogSprite.setPosition((stage.getWidth()/2)-(dialogSprite.getWidth()/2), (stage.getHeight()/2)-(dialogSprite.getHeight()/2));
		
		
		// Enter Name Label
		labelHelper = new LabelHelper("Enter your name:", 16, Color.WHITE, "opensans.ttf"); 
		Label enterNameLabel = labelHelper.getLabel();
		enterNameLabel.setAlignment(Align.bottomLeft);
		enterNameLabel.setPosition(dialogSprite.getX()+50, dialogSprite.getY()+dialogSprite.getHeight()-125);
		
		// Enter Name Textbox
		TextFieldHelper tfHelper = new TextFieldHelper((int) (dialogSprite.getWidth()-100), 35, labelHelper.getFont());
		
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
		saveButtonHelper = new ButtonHelper("menu/save-button.png", 204, 63, 0, 0, 0, 63);
		
		ImageButton saveButton = saveButtonHelper.getButton();
		saveButton.setPosition((stage.getWidth()/2)-(saveButton.getWidth()/2), dialogSprite.getY()+50);
		
		// Cancel Button
		cancelButtonHelper = new ButtonHelper("menu/save-score-popup/cancel-button.png", 71, 71, 0, 0, 0, 71);
		cancelButton = cancelButtonHelper.getButton();
		
		cancelButton.setPosition((dialogSprite.getX()+dialogSprite.getWidth()-(cancelButton.getWidth()/2)-15), dialogSprite.getY()+dialogSprite.getHeight()-cancelButton.getHeight()+15);
		
		
		// Button Listeners
		cancelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
			}
		});
		
		saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				error = false;
				messageLabel.setVisible(error);
				
				if(!scoreSubmitted && !nameText.getText().isEmpty()) {
				
					HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
					httpPost.setUrl("http://localhost/spacetitans/Score_Upload.php");
					httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
					httpPost.setContent("score="+(ShooterGame.PLAYER_SCORE+bonusPoints)+"&name="+nameText.getText()+"&level="+ShooterGame.CURRENT_LEVEL);
	
					Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
						
						String status;
						
						public void handleHttpResponse(HttpResponse httpResponse) {
							status = httpResponse.getResultAsString();
							System.out.println(status);
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
						}
	
						@Override
						public void cancelled() {
							// TODO Auto-generated method stub
							
						}
						
					});

				
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
		stage.addActor(cancelButton);
		stage.addActor(saveButton);
		stage.addActor(enterNameLabel);
		stage.addActor(nameText);
		
	}
	
	public void draw(SpriteBatch batch) {
		
		if(!hidden) {
			fadedBG.draw(batch);
			dialogSprite.draw(batch);
			
			if(error) {
				errorBorder.draw(batch);
			}
		}
		
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void hide() {
		hidden = true;
		error = false;
		messageLabel.setVisible(false);
		Gdx.input.setInputProcessor(parentStage);
		
	}
	
	public void show() {
		hidden = false;
		error = false;
		messageLabel.setVisible(false);
		Gdx.input.setInputProcessor(stage);
	}
	
	public boolean isVisible() {
		return !hidden;
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
}
