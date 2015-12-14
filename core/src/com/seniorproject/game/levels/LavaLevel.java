package com.seniorproject.game.levels;

import com.seniorproject.game.ShooterGame;
import com.seniorproject.game.enemies.Boss;
import com.seniorproject.game.enemies.LavaBoss;

public class LavaLevel extends Level {

	public LavaLevel(ShooterGame g) {
		super(g, "enemy2.png", "asteroid.png", "lava-boss.png", "lava.jpg");
	}

	@Override
	public Boss getBoss() {
		return new LavaBoss(this, bossSpriteFile);
	}

}
