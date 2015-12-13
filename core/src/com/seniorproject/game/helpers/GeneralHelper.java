package com.seniorproject.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.ShooterGame;


public class GeneralHelper {

	public static void disposeSound(final Sound sound, float time) {
		
		Timer.schedule(new Task() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				sound.dispose();
			}
			
		}, time);
		
	}
	
	
	public static String formatScore(int score) {
		String formattedScore = score+"";
		
		if(score >= 1000000) {
			formattedScore = String.format("%.1f", (score/1000000.0))+"M";
		}
		else if(score >= 1000) {
			formattedScore = String.format("%.1f", (score/1000.0))+"K";
		}

		
		return formattedScore;
	}
	
	public static SequenceAction shakeSprite(final Sprite sprite, final float amount) {
		
		SequenceAction sa = Actions.sequence(
				
				Actions.run(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						sprite.rotate(amount);
					}
					
				}),
				Actions.delay(.05f),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						sprite.rotate((amount*2)*-1);
					}
					
				}),
				Actions.delay(.05f),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						sprite.rotate(amount);
					}
					
				})
			);
		
		
		return sa;
	}
	
	public static SequenceAction shakeSprite(final Sprite sprite) {
		return shakeSprite(sprite, 5);
	}
	
	public static void login(String username, String password) {
		
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://spacetitans.tk/ingame-login.php");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setContent("username="+username+"&password="+password);

		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
			
			String status;
			
			public void handleHttpResponse(HttpResponse httpResponse) {
				status = httpResponse.getResultAsString();
				System.out.println(status);
			}

			public void failed(Throwable t) {
				status = "Unable to submit score at this time!";
				System.out.println(status);
			}

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				status = "cancelled";
				System.out.println(status);
			}
			
		});
		
	}
	
}
