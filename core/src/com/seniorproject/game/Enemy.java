package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Enemy extends GameActor {
	
	private int health = 100;
	private Texture texture = new Texture(Gdx.files.internal("enemy.png"));
	private Sprite enemySprite = new Sprite(texture);
	
	
	public Enemy(World world) {
		super(world);
		collisionData.setActorType("Enemy");
	}
	
	
	public void act(float delta) {
		super.act(delta);
		
		if(health <= 0) {
			setDead(true);
		}
	}
	
	public void draw(Batch batch, float alpha) {
		enemySprite.draw(batch);
	}
	
	
	@Override
	public void setStage(Stage stage) {
		super.setStage(stage);
		
		if(stage != null) {
			
			setBounds(0, (stage.getHeight()-(enemySprite.getHeight()+10)), enemySprite.getWidth(), enemySprite.getHeight());
			
		}
		
	}
	
	
	@Override
	protected void positionChanged() {
		enemySprite.setPosition(getX(), getY());
		createBody();
		super.positionChanged();
	}


	@Override
	public void createBody() {

		if(getStage() != null && !isDead()) {
			float bodyXOffset = (getParent().getStage().getWidth()/2)-(enemySprite.getWidth()/2);
			float bodyYOffset = (getParent().getStage().getHeight()/2)-(enemySprite.getHeight()/2);
			
			// DESTROY THE CURRENT BODY IF THERE IS ONE
			if(body != null) {
				body.destroyFixture(fixture);
			}
			
			// CREATE A NEW BODY
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			bodyDef.position.set(enemySprite.getX()-bodyXOffset, enemySprite.getY()-bodyYOffset);
			
			shape = new PolygonShape();
			shape.setAsBox(enemySprite.getWidth()/2, enemySprite.getHeight()/2);
			
			
			body = getWorld().createBody(bodyDef);
			fixture = body.createFixture(shape, 0f);
			fixture.setUserData(collisionData);
			body.resetMassData();
			shape.dispose();
		}
		else if(getStage() != null && isDead()) {
			body.destroyFixture(fixture);
			this.remove();
		}
	}
	
	@Override
	public String toString() {
		return "Enemy";
	}
	
	public void lowerHealth(int damage) {
		health -= damage;
	}
	

}
