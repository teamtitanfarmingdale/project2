package com.seniorproject.game.powerups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.levels.Level;

public class RandomPowerup extends BasePowerup {
	
	PowerupSpawner spawner;
	BasePowerup powerup;
	Label powerupLabel;
	
	public RandomPowerup(Level l) {
		super(l);
		spawner = l.powerupSpawner;
		actualHeight = 48;
		sprite.setRegion(0, spriteHeight*5, spriteWidth, actualHeight);
		sprite.setSize(spriteWidth, actualHeight);
		
		setParticleColor(Powerup.RANDOM_COLOR);
		
		LabelHelper labelHelper = new LabelHelper("", 16, Color.WHITE, l.game);
		powerupLabel = labelHelper.getLabel();
		powerupLabel.setWidth(spriteWidth);
		powerupLabel.setAlignment(Align.center);
		
	}

	
	@Override
	public void applyPowerup() {
		
		int randomPowerup = -1;
		while(randomPowerup == -1) {
			randomPowerup = (int) Math.ceil((Math.random()*(Powerup.TOTAL_POWERUPS-1)));
			randomPowerup = spawner.checkRandomPowerup(randomPowerup);
			
			if(randomPowerup == Powerup.RANDOM) {
				System.out.println("SOME HOW SELECTED RANDOM: "+randomPowerup);
				randomPowerup = -1;
			}
			
		}
		
		powerup = spawner.getPowerup(randomPowerup);
		powerup.applyPowerup();
		
		try {
			powerupLabel.setText(Powerup.NAMES[randomPowerup]);
		}
		catch(Exception e) {
			// Do nothing - ** Just a precaution, shouldn't ever get here **
		}
		
		powerupLabel.setPosition(getX(), getY());
		
		level.addGameObject(powerupLabel);
		
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0f, 50f);
		mba.setDuration(2f);
		powerupLabel.addAction(mba);
		powerupLabel.addAction(Actions.fadeOut(2f));
	}

}
