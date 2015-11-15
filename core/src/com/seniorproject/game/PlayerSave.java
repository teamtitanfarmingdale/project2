package com.seniorproject.game;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class PlayerSave {
	
	public int player_id = 0;
	public String name = "";
	public int score = 0;
	public int level = 0;
	public Calendar date = new GregorianCalendar();

	
	@Override
	public String toString() {
		String returnString = "";
		
		returnString += "Player ID: "+player_id;
		returnString += "\nName: "+name;
		returnString += "\nScore: "+score;
		returnString += "\nLevel: "+level;
		returnString += "\nDate: "+date.toString();
		
		return returnString;
	}
	
	
}
