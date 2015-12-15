package com.seniorproject.game;

import java.util.Calendar;
import java.util.TimeZone;



public class PlayerSave {
	
	public int playersave_id = 0;
	public int player_id = 0;
	public String name = "";
	public int score = 0;
	public int level_id = 0;
	public int wave_reached = 0;
	public int lives = 0;
	public int rockets = 0;
	public int emp = 0;
	public Calendar date;

	public PlayerSave() {
		date = Calendar.getInstance();
		
	}
	
	public String getDateFormatted() {
		String returnString = "";

		Calendar formattedDate = Calendar.getInstance();
		
		long offset = TimeZone.getDefault().getOffset(date.getTimeInMillis());

		formattedDate.setTimeInMillis(date.getTimeInMillis()+offset);
		
		int hour = formattedDate.get(Calendar.HOUR);
		hour = (hour == 0) ? 12 : hour;
		
		int minute = formattedDate.get(Calendar.MINUTE);
		String displayMinute = (minute < 10) ? "0"+minute : minute+"";
		
		int seconds = formattedDate.get(Calendar.SECOND);
		String displaySecond = (seconds < 10) ? "0"+seconds : seconds+"";
		
		String amPM = (formattedDate.get(Calendar.AM_PM) == 1) ? "PM" : "AM";
		
		
		returnString += formattedDate.get(Calendar.MONTH);
		returnString += "/";
		returnString += formattedDate.get(Calendar.DATE);
		returnString += "/";
		returnString += formattedDate.get(Calendar.YEAR);
		returnString += " ";
		returnString += hour;
		returnString += ":";
		returnString += displayMinute;
		returnString += ":";
		returnString += displaySecond;
		returnString += " "+amPM;
		
		return returnString;
	}
	
	@Override
	public String toString() {
		String returnString = "";
		
		returnString += "Player ID: "+player_id;
		returnString += "\nName: "+name;
		returnString += "\nScore: "+score;
		returnString += "\nLevelID: "+level_id;
		returnString += "\nLevel: "+wave_reached;
		returnString += "\nDate: "+date.toString();
		
		return returnString;
	}
	
	
}
