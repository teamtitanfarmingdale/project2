package com.seniorproject.game.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.game.helpers.LabelHelper;

public class NewGameSlot extends Slot {

	LabelHelper newGameLabelHelper;
	Label newGameLabel;
	
	public NewGameSlot(PlayMenu menu) {
		super(null, menu);
		
		newGameLabelHelper = new LabelHelper("NEW GAME", 24, Color.WHITE);
		newGameLabel = newGameLabelHelper.getLabel();
		newGameLabel.setPosition(0, 32);
		newGameLabel.setWidth(this.selectButtonImage.getWidth()-19);
		newGameLabel.setAlignment(Align.bottom);
		this.addActor(newGameLabel);
		
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		selectButtonImage.setPosition(newGameLabel.getX()-10, newGameLabel.getY()-(selectButtonImage.getHeight()/2)+14);
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		newGameLabelHelper.dispose();
	}
	
}
