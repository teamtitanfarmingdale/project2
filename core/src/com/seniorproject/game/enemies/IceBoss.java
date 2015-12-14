package com.seniorproject.game.enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.seniorproject.game.levels.Level;

public class IceBoss extends Boss {

	protected PolygonShape leftArmShape;
	
	public IceBoss(Level l, String spriteFile) {
		super(l, spriteFile);

	}

	@Override
	public void initBossFile() {
		bossFile = "boss2.png";
	}
	
	
	@Override
	public void buildShape() {
		
		float leftArm[] = {
				97, 7,
				101, -82,
				119, -110,
				135, -100,
				150, -73,
				150, 10,
				107, 68,
				43, 72
		};
		
		// done
		float rightArm[] = {
				-90, 3,
				-102, -98,
				-124, -109,
				-139, -91,
				-148, 3,
				-118, 51,
				-88, 66,
				-29, 66
		};
		
		float leftAntenna[] = {
				18, -67,
				36, -84,
				44, -113,
				39, -146,
				66, -100,
				60, 6,
				12, 144,
				5, -117
		};
		
		// done
		float rightAntenna[] = {
				-16, -70,
				-33, -93,
				-35, -152,
				-63, -80,
				-39, 91,
				2, 155,
				4, -117
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
		}
	}
}
