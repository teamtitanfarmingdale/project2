package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.seniorproject.game.levels.Level;

public class LavaBoss extends Boss {

	protected PolygonShape leftArmShape;
	protected PolygonShape leftLegsShape;
	protected PolygonShape rightLegsShape;
	
	public LavaBoss(Level l, String spriteFile) {
		super(l, spriteFile);
	}

	
	@Override
	public void initBossFile() {
		bossFile = "lava-boss.png";
	}
	
	
	@Override
	public void buildShape() {
		
		// left part of body
		float leftArm[] = {
				-1, -40,
				44, -38,
				91, -19,
				119, 8,
				116, 32,
				89, 66,
				58, 81,
				2, 86
		};
		
		// right part of body
		float rightArm[] = {
				-2, -42,
				-48, -33,
				-97, -13,
				-122, 19,
				-100, 58,
				-37, 83,
				0, 85
		};
		
		// left legs
		float leftLegs[] = {
				119, 16,
				134, 6,
				134, -50,
				113, -10,
				108, -67,
				94, -38,
				73, -24,
				102, 2
		};
		
		// right legs
		float rightLegs[] = {
				-117, 16,
				-135, 7,
				-133, -49,
				-108, -21,
				-107, -72,
				-90, -37,
				-70, -27,
				-100, -2
		};
		
		float leftAntenna[] = {
				39, -37,
				50, -52,
				60, -89,
				67, -46,
				60, -32
		};
		
		float rightAntenna[] = {
				-34, -32,
				-48, -55,
				-58, -88,
				-65, -49,
				-51, -16
		};
		
		shape = new PolygonShape();
		shape.set(leftArm);
		
		rightArmShape = new PolygonShape();
		rightArmShape.set(rightArm);
		
		leftArmShape = new PolygonShape();
		leftArmShape.set(leftArm);
		
		leftAntennaShape = new PolygonShape();
		leftAntennaShape.set(leftAntenna);
		
		rightAntennaShape = new PolygonShape();
		rightAntennaShape.set(rightAntenna);
		
		rightLegsShape = new PolygonShape();
		rightLegsShape.set(rightLegs);
		
		leftLegsShape = new PolygonShape();
		leftLegsShape.set(leftLegs);
		
	}
	
	@Override
	protected void createBodyBoundry() {
		
		float bodyXOffset = (getParent().getStage().getWidth()/2)-(sprite.getWidth()/2);
		float bodyYOffset = (getParent().getStage().getHeight()/2)-(sprite.getHeight()/2);
		
		// CREATE A NEW BODY
		if(bodyDef == null) {
			bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bodyDef.position.set(sprite.getX()-bodyXOffset, sprite.getY()-bodyYOffset);
		body = getWorld().createBody(bodyDef);
		

		fixture = body.createFixture(shape, 0f);
		fixture.setUserData(collisionData);

		if(leftArmShape != null) {
			
			fixture = body.createFixture(leftArmShape, 0f);
			fixture.setUserData(collisionData);
			
			fixture = body.createFixture(rightArmShape, 0f);
			fixture.setUserData(collisionData);
	
			fixture = body.createFixture(leftLegsShape, 0f);
			fixture.setUserData(collisionData);
			
			fixture = body.createFixture(rightLegsShape, 0f);
			fixture.setUserData(collisionData);
	
			fixture = body.createFixture(leftAntennaShape, 0f);
			fixture.setUserData(collisionData);
	
			fixture = body.createFixture(rightAntennaShape, 0f);
			fixture.setUserData(collisionData);

		}
		
		body.resetMassData();
		//shape.dispose();
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		
		if(leftArmShape != null) {
			leftArmShape.dispose();
			leftLegsShape.dispose();
			rightLegsShape.dispose();
		}
	}
	
}
