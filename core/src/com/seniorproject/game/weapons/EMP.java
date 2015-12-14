package com.seniorproject.game.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.seniorproject.game.GameActor;
import com.seniorproject.game.enemies.BaseEnemy;
import com.seniorproject.game.enemies.Enemy;
import com.seniorproject.game.levels.Level;
import com.seniorproject.game.particles.InfiniteParticle;

public class EMP extends BaseBullet {

	InfiniteParticle empCloud;
	
	public EMP(Level l) {
		super(l, "emp2.png");
		actualWidth = 120f;
		//shouldCreateBody = false;
		collisionData.setActorType("EMP");
		damage = 0;
		
		particleInterface = l.game.assetManager.getParticle("particles/emp.particle", "InfiniteParticle");
		empCloud = (InfiniteParticle) particleInterface.asset;
		empCloud.start(this.getX()+(bulletSprite.getWidth()/2), this.getY()+bulletSprite.getHeight()+100);
	}


	@Override
	public boolean remove() {
		super.remove();
		
		empCloud.stop();
		level.game.assetManager.releaseParticle(particleInterface, 0f);
		
		return true;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		MoveByAction mba = new MoveByAction();
		mba.setAmount(0,  movementDistance);
		mba.setDuration(bulletSpeed);
		addAction(mba);
		
	}
	
	@Override
	public void draw(Batch batch, float delta) {
		super.draw(batch, delta);
		empCloud.start(this.getX()+(bulletSprite.getWidth()/2), this.getY()+bulletSprite.getHeight()+150);
		empCloud.draw(batch);
	}
	
	
	@Override
	protected void createBodyBoundry() {
		
		float bodyXOffset = (getParent().getStage().getWidth()/2)-(bulletSprite.getWidth()/2);
		float bodyYOffset = (getParent().getStage().getHeight()/2)-(bulletSprite.getHeight()/2);
		
		// CREATE A NEW BODY
		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(bulletSprite.getX()-bodyXOffset, bulletSprite.getY()-bodyYOffset);
		
		shape = new PolygonShape();
		
		//Vector2[] vertices = new Vector2[5];
		
		float[] vertices = {
				-1, 94,
				-59, 81,
				-99, 53,
				-123, -1,
				122, 1,
				106, 41,
				67, 81
		};
		
		/*
		vertices[0] = new Vector2(0f, -96f);
		vertices[1] = new Vector2(-29f, -93f);
		vertices[2] = new Vector2(-68f, -77f);
		vertices[3] = new Vector2(-99f, -46f);
		vertices[4] = new Vector2(-118f, -10f);
		vertices[5] = new Vector2(-126f, 16f);
		vertices[6] = new Vector2(-122f, 41f);
		vertices[7] = new Vector2(-97f, 73f);
		vertices[8] = new Vector2(93f, 81f);
		vertices[9] = new Vector2(120f, 54f);
		vertices[10] = new Vector2(122f, 25f);
		vertices[11] = new Vector2(116f, -15f);
		vertices[12] = new Vector2(88f, -60f);
		vertices[13] = new Vector2(33f, -96f);
		*/
		
		/*
		vertices[0] = new Vector2(-106f, -43f);
		vertices[1] = new Vector2(-2f, -97f);
		vertices[2] = new Vector2(102f, -44f);
		vertices[3] = new Vector2(125f, 18f);
		vertices[4] = new Vector2(-125f, 20f);
		*/
		
		
		
		/*
		vertices[0] = new Vector2(-105f, -35f);
		vertices[1] = new Vector2(105f, -35f);
		vertices[2] = new Vector2(104f, 66f);
		vertices[3] = new Vector2(-106f, 65f);
		*/
		
		
		shape.set(vertices);
		
		//shape.setAsBox(actualWidth, bulletSprite.getHeight()/2);

	}
	
	@Override
	public void setCollidedEnemy(GameActor enemy) {
		super.setCollidedEnemy(enemy);

		BaseEnemy collidedEnemy = (BaseEnemy) enemy;
		collidedEnemy.hitByEMP = true;

		collisionParticle.stop();
	}
	
	
}
