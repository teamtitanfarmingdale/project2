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
	
	
}
