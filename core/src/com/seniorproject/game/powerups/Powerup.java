package com.seniorproject.game.powerups;

public class Powerup {

	public final static float[] ROCKET_COLOR 	= { 42/255.0f, 118/255.0f, 55/255.0f };
	public final static float[] EMP_COLOR 		= { 34/255.0f, 52/255.0f, 202/255.0f };
	public final static float[] HEALTH_COLOR 	= { 237/255.0f, 28/255.0f, 36/255.0f };
	public final static float[] ARMOR_COLOR 	= { 189/255.0f, 111/255.0f, 47/255.0f };
	public final static float[] SHIELD_COLOR 	= { 9/255.0f, 166/255.0f, 169/255.0f };
	public final static float[] RANDOM_COLOR 	= { 109/255.0f, 12/255.0f, 255/255.0f };
	public final static float[] LIFE_COLOR 		= { 19/255.0f, 110/255.0f, 183/255.0f };
	
	
	public final static int ROCKET 	= 1;
	public final static int EMP 	= 2;
	public final static int HEALTH 	= 3;
	public final static int ARMOR 	= 4;
	public final static int SHIELD 	= 5;
	public final static int LIFE 	= 6;
	public final static int RANDOM 	= 7; // RANDOM SHOULD ALWAYS BE SET TO THE LAST POWERUP
	
	public final static int TOTAL_POWERUPS = 7;
	
	// Percentage chance for each power up
	public final static float[] CHANCES = {0, .5f, .3f, .5f, .5f, .4f, .5f, .3f };
	
	public final static String[] NAMES = {
		"",
		"Rocket",
		"EMP",
		"Health",
		"Armor",
		"Shield",
		"Life",
		"Random"
	};
	
	public final static float SHIELD_TIME = 10f;
	
}
