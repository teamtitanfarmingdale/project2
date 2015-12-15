package com.seniorproject.game.levels;

import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.enemies.Boss;
import com.seniorproject.game.enemies.IceBoss;

public class IceLevel extends Level {

	
	public IceLevel(ShooterGame g) {
		super(g, "enemy2.png", "asteroid.png", "boss2.png", "iceback-01.jpg");
	}
	
	@Override
	public Boss getBoss() {
		return new IceBoss(this, bossSpriteFile);
	}
	
	@Override
	public void initLevelID() {
		level_id = 2;
	}

}
