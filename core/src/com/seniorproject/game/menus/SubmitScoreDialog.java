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
		
	Sprite errorBorder;
	LabelHelper messageLabelHelper;
	Label messageLabel;
	
	TextFieldHelper tfHelper;
	
	public SubmitScoreDialog(Stage parentStage, ShooterGame g) {
		super(parentStage, g);
		
		// Enter Name Label
		labelHelper = new LabelHelper("Submitting Score...", 16, Color.WHITE, "opensans.ttf", game); 
		Label enterNameLabel = labelHelper.getLabel();
		enterNameLabel.setAlignment(Align.center);
		enterNameLabel.setWidth(dialogSprite.getWidth());
		enterNameLabel.setPosition(dialogSprite.getX(), dialogSprite.getY()+dialogSprite.getHeight()-125);
		
		// Enter Name Textbox
		tfHelper = new TextFieldHelper((int) (dialogSprite.getWidth()-100), 35, game);
		
		final TextField nameText = tfHelper.getTextField();
		nameText.setPosition((stage.getWidth()/2)-(nameText.getWidth()/2), enterNameLabel.getY()-40);
		
		/* Error Notifications */
		
		// Error Border
		Texture errorBorderTexture = game.assetManager.getTexture("colors/pink.png");
		errorBorderTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		errorBorder = new Sprite(errorBorderTexture);
		errorBorder.setSize(nameText.getWidth()+4, nameText.getHeight()+4);
		errorBorder.setPosition(nameText.getX()-2, nameText.getY()-2);
		
		// Error Label
		messageLabelHelper = new LabelHelper("You must enter a name!", 16, Color.WHITE, "opensans.ttf", game);
		messageLabel = messageLabelHelper.getLabel();
		messageLabel.setColor(1f,.5f, .5f, 1f);
		messageLabel.setAlignment(Align.bottom);
		messageLabel.setWidth(dialogSprite.getWidth());
		messageLabel.setPosition(dialogSprite.getX(), dialogSprite.getY()+dialogSprite.getHeight()-85);
		messageLabel.setVisible(false);

		stage.addActor(messageLabel);
		//stage.addActor(saveButton);
		stage.addActor(enterNameLabel);
		//stage.addActor(nameText);
		
	}
	
	public void submitScore() {
		
		error = false;
		messageLabel.setVisible(error);

		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://spacetitans.tk/Score_Upload.php");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setContent("score="+(ShooterGame.PLAYER_SCORE+bonusPoints)+"&scoreplayer_id="+ShooterGame.PLAYER_ID+"&level_id="+ShooterGame.CURRENT_LEVEL);

		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
			
			String status;
			
			public void handleHttpResponse(HttpResponse httpResponse) {
				status = httpResponse.getResultAsString();
			
				System.out.println("status");
				System.out.println(status);
				
				labelHelper.getLabel().setVisible(false);
				
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
		submitScore();
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
		//fadedBGTexture.dispose();
		labelHelper.dispose();
		messageLabelHelper.dispose();
		cancelButtonHelper.dispose();
		stage.dispose();
		//errorBorder.getTexture().dispose();
	}
}
