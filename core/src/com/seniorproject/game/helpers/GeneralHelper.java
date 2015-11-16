package com.seniorproject.game.helpers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

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
	
	
}
