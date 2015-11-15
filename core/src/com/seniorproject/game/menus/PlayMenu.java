package com.seniorproject.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.ShooterGame;

public class PlayMenu extends BaseMenu {

	public PlayMenu(ShooterGame g) {
		super(g);
		create();
	}

	public void create() {
		
		int borderOffset = 80;
		int buttonOffset = 20;
		
		init("menu/play-menu.png", borderOffset);
	
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		SaveSlot slot = new SaveSlot();
		SaveSlot slot2 = new SaveSlot("colors/white.png");
		SaveSlot slot3 = new SaveSlot();
		SaveSlot slot4 = new SaveSlot("colors/white.png");
		SaveSlot slot5 = new SaveSlot();
		SaveSlot slot6 = new SaveSlot("colors/white.png");
		SaveSlot slot7 = new SaveSlot();
		SaveSlot slot8 = new SaveSlot("colors/white.png");
		
		Table containerTable = new Table();
		Table saveListTable = new Table();
		
		saveListTable.add(slot);
		saveListTable.row();
		saveListTable.add(slot2);
		saveListTable.row();
		saveListTable.add(slot3);
		saveListTable.row();
		saveListTable.add(slot4);
		saveListTable.row();
		saveListTable.add(slot5);
		saveListTable.row();
		saveListTable.add(slot6);
		saveListTable.row();
		saveListTable.add(slot7);
		saveListTable.row();
		saveListTable.add(slot8);

		saveListTable.setDebug(true);
		//saveListTable.setSize(300, 200);
		ScrollPane scrollPane = new ScrollPane(saveListTable, skin);
		scrollPane.setSize(500, 200);
		scrollPane.setFlickScroll(false);
		scrollPane.setFadeScrollBars(false);

		/*
		scrollPane.layout();
		containerTable.setFillParent(true);
		containerTable.add(scrollPane).fill().expand();
		containerTable.row();
		containerTable.setSize(300, 600);
		containerTable.setPosition(stage.getWidth()/2 - containerTable.getWidth()/2, stage.getHeight()/2 - containerTable.getHeight()/2);
		
		*/
		scrollPane.setPosition(stage.getWidth()/2 - scrollPane.getWidth()/2, stage.getHeight()/2 - scrollPane.getHeight()/2);
		
		
		stage.addActor(scrollPane);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		
		this.batch.begin();
		this.menuBorder.draw(batch);
		this.batch.end();
		
		
	}
	
}
