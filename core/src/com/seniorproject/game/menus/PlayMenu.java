package com.seniorproject.game.menus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.PlayerSave;
import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.helpers.ButtonHelper;
import com.seniorproject.game.helpers.LabelHelper;
import com.seniorproject.game.helpers.ScrollPaneHelper;

public class PlayMenu extends BaseMenu {

	Sprite logo;
	
	ArrayList<Slot> saveSlots;
	
	ButtonHelper playSavedGameHelper;
	ButtonHelper backButtonHelper;
	
	Texture playSavedDisabledTexture;
	Image playSavedGameDisabled;
	
	NewGameDialog dialog;
	
	Slot selectedSave;
	
	public PlayMenu(ShooterGame g) {
		super(g);
		create();
	}

	@Override
	public void dispose() {
		super.dispose();
		playSavedDisabledTexture.dispose();
		backButtonHelper.dispose();
		playSavedGameHelper.dispose();
		dialog.dispose();
		logo.getTexture().dispose();
		
		for(Slot slot : saveSlots) {
			slot.dispose();
		}
	}
	
	public void create() {
		
		int borderOffset = 80;
		int buttonOffset = 20;
		
		init("menu/mainmenu.png", borderOffset);
	
		
		PlayerSave tempSave = new PlayerSave();
		tempSave.name = "LeooLeooLeooLeoo";
		tempSave.score = 1005000;
		tempSave.level = 1;
		tempSave.player_id = 2;
		//game.db.savePlayer(tempSave);
		
		
		// LOGO
		Texture logoTexture = new Texture(Gdx.files.internal("logo-small.png"));
		logo = new Sprite(logoTexture);
		logo.setPosition((stage.getWidth()/2)-(logo.getWidth()/2), stage.getHeight()-logo.getHeight());
		
		
		
		dialog = new NewGameDialog(stage, this);
		
		// Get all game saves
		saveSlots = new ArrayList<Slot>();
		
		// New Game Slot
		saveSlots.add(new NewGameSlot(this));
		
		// Saved Game Slots
		for(PlayerSave save : game.db.getPlayerSaves()) {
			saveSlots.add(new SaveSlot(save, this));
		}

		// Setup Save List
		VerticalGroup saveListTable = new VerticalGroup();
		int listHeight = 0;
		int saveSlotSpacing = 10;
		saveListTable.pad(0);
		
		//saveListTable.space(saveSlotSpacing);
		if(saveSlots.size() > 0) {
			
			Actor spacer = new Actor();
			int slotCounter = 0;
			listHeight += saveSlotSpacing;
			for(Slot saveSlot : saveSlots) {
				saveListTable.addActor(saveSlot);
				listHeight += saveSlot.getSelectButtonImage().getHeight()+saveSlotSpacing;
			
				if(slotCounter == 0) {
					spacer = new Actor();
					spacer.setSize(200, 15);
					saveListTable.addActor(spacer);
				}
				
				slotCounter++;
				
				spacer = new Actor();
				spacer.setSize(200, 10);
				saveListTable.addActor(spacer);
			}
			
		}
		
		System.out.println("List Height:"+listHeight);
		if(listHeight < 206) {
			for(Slot saveSlot : saveSlots) {
				saveSlot.setWidth(saveSlot.getWidth()+19);
			}
		}

		// Setup Scroll Pane
		ScrollPaneHelper spHelper = new ScrollPaneHelper(saveListTable);
		
		final ScrollPane scrollPane = spHelper.getScrollPane();
		scrollPane.setSize(280, 180);
		scrollPane.setFlickScroll(false);
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollingDisabled(true, false);

		
		
		scrollPane.setPosition(stage.getWidth()/2 - scrollPane.getWidth()/2 + 5, stage.getHeight()/2 - scrollPane.getHeight()/2 - 55);
		stage.addActor(scrollPane);

		// Auto touch scroll pane so you can scroll with mouse wheel without clicking
		stage.touchDown((int) scrollPane.getX()+5, (int) (stage.getHeight()/2), 0, 0);

		
		// Setup Buttons
		
		playSavedDisabledTexture = new Texture(Gdx.files.internal("menu/play-disabled-button.png"));
		playSavedGameDisabled = new Image(playSavedDisabledTexture);
		
		playSavedGameDisabled.setPosition((stage.getWidth()/2) - (playSavedGameDisabled.getWidth()/2), menuBorder.getY()+borderOffset+buttonOffset);
		stage.addActor(playSavedGameDisabled);
		
		
		playSavedGameHelper = new ButtonHelper("menu/play-button.png", 204, 63, 0, 0, 0, 63);
		ImageButton playButton = playSavedGameHelper.getButton();
		playButton.setPosition(playSavedGameDisabled.getX(), playSavedGameDisabled.getY());
		playButton.setVisible(false);
		stage.addActor(playButton);
		
		backButtonHelper = new ButtonHelper("menu/back-button.png", 204, 63, 0, 0,0, 63);
		ImageButton backButton = backButtonHelper.getButton();
		backButton.setPosition((stage.getWidth()/2) - (backButton.getWidth()/2), playButton.getY()-playButton.getHeight()-(buttonOffset/4));
		stage.addActor(backButton);
		
		// Setup Button Listener
		
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(selectedSave != null) {
					
					if(selectedSave.getPlayerSave() == null) {
						// New Game Selected
						dialog.show();
					}
					else {
						// Load Game
						ShooterGame.CURRENT_LEVEL = selectedSave.getPlayerSave().level;
						ShooterGame.PLAYER_SCORE = selectedSave.getPlayerSave().score;
						ShooterGame.PLAYER_SAVE = selectedSave.getPlayerSave();
						game.switchScreen(ShooterGame.LOADED_GAME);
					}
					
				}
				else {
					System.out.println("no save selected");
				}
			}
		});
		
		
		backButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.switchScreen(ShooterGame.MAIN_MENU);
			}
		});
		
		
		Gdx.input.setInputProcessor(stage);
		
		
		
		if(saveSlots.size() == 1) {
			this.selectSaveSlot(saveSlots.get(0));
			dialog.show();
		}
		
		
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
		
		this.batch.begin();
		this.menuBorder.draw(batch);
		this.logo.draw(batch);
		this.dialog.draw(batch);
		this.batch.end();
		
		if(dialog.isVisible()) {
			dialog.getStage().act(Gdx.graphics.getDeltaTime());
			dialog.getStage().draw();
		}
		
	}
	
	public void selectSaveSlot(Slot saveSlot) {
		
		saveSlots.remove(saveSlot);
		
		for(Slot slot : saveSlots) {
			slot.reset();
		}
		
		saveSlots.add(saveSlot);
		
		if(saveSlot.select()) {
			selectedSave = saveSlot;
			playSavedGameDisabled.setVisible(false);
			playSavedGameHelper.getButton().setVisible(true);
		}
		else {
			selectedSave = null;
			playSavedGameDisabled.setVisible(true);
			playSavedGameHelper.getButton().setVisible(false);
		}
	}
	
}
