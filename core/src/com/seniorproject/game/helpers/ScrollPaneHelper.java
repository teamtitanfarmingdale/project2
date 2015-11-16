package com.seniorproject.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class ScrollPaneHelper {
	
	
	ScrollPane scrollPane;
	
	Sprite verticalScrollbar;
	Sprite scrollKnob;
	

	
	public ScrollPaneHelper(Actor actor) {
		
		
		ScrollPaneStyle spStyle = new ScrollPaneStyle();
		
		Texture scrollBarTexture = new Texture(Gdx.files.internal("menu/vertical-slidebar.png"));
		Texture scrollKnobTexture = new Texture(Gdx.files.internal("menu/vertical-slidemark.png"));
		
		verticalScrollbar = new Sprite(scrollBarTexture);
		scrollKnob = new Sprite(scrollKnobTexture);
		scrollKnob.setSize(20, 16);
		
		SpriteDrawable scrollbarDrawable = new SpriteDrawable(verticalScrollbar);
		SpriteDrawable scrollKnobDrawable = new SpriteDrawable(scrollKnob);

		spStyle.vScroll = scrollbarDrawable;
		spStyle.vScrollKnob = scrollKnobDrawable;
		
		scrollPane = new ScrollPane(actor, spStyle);
	
	
	}
	
	public ScrollPane getScrollPane() {
		return scrollPane;
	}
	
	
	public void dispose() {
		verticalScrollbar.getTexture().dispose();
		scrollKnob.getTexture().dispose();
	}
}
