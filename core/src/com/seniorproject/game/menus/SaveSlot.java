package com.seniorproject.game.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.seniorproject.game.PlayerSave;
import com.seniorproject.game.helpers.GeneralHelper;
import com.seniorproject.game.helpers.LabelHelper;

public class SaveSlot extends Slot {

	LabelHelper bottomLabelHelper;
	LabelHelper topLabelHelper;
	LabelHelper middleLabelHelper;
	
	
	Label topLabel;
	Label bottomLabel;
	Label middleLabel;

	public SaveSlot(PlayerSave save, PlayMenu menu) {
		super(save, menu);

		topLabelHelper = new LabelHelper(save.name, 14, Color.WHITE, menu.game);
		topLabel = topLabelHelper.getLabel();
		topLabel.setPosition(0, 70);
		
		middleLabelHelper = new LabelHelper(save.getDateFormatted(), 14, Color.WHITE, menu.game);
		middleLabel = middleLabelHelper.getLabel();
		middleLabel.setPosition(0, topLabel.getY()-topLabel.getHeight()+8);
		
		bottomLabelHelper = new LabelHelper(GeneralHelper.formatScore(save.score)+" Points - Level: "+save.wave_reached, 12, Color.WHITE, menu.game);
		bottomLabel = bottomLabelHelper.getLabel();
		bottomLabel.setPosition(0, middleLabel.getY()-middleLabel.getHeight()+8);
		

		this.addActor(topLabel);
		this.addActor(middleLabel);
		this.addActor(bottomLabel);
	
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		selectButtonImage.setPosition(topLabel.getX()-10, topLabel.getY()-(selectButtonImage.getHeight())+topLabel.getHeight()+8);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		topLabelHelper.dispose();
		bottomLabelHelper.dispose();
		middleLabelHelper.dispose();
	}
}
