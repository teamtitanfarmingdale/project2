package com.seniorproject.game;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import javax.swing.JOptionPane;

public class Database {

	private String dbName = "spacetitans.db";
	Connection c = null;
	Statement stmt = null;
	
	public Database() {
		setup();
	}
	
	public void setup() {
		
		if(connect()) {
			createPlayerTable();
			createScoresTable();
		}
		
	}
	
	public boolean connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+dbName);
		}
		catch(Exception e) {
			System.err.println("Error connecting to database.\n"+e.getMessage());
			System.exit(0);
		}
		
		return c != null;
	}
	
	public void disconnect() {
		try {
			c.close();			
		}
		catch (Exception e) {
			System.err.println("Error connecting to database.\n"+e.getMessage());
			System.exit(0);
		}
	}
	
	private void createPlayerTable() {

		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE playersave (" +
						 "playersave_id INTEGER PRIMARY KEY," +
						 "player_id INTEGER," +
						 "name VARCHAR(255) NOT NULL," +
						 "score INTEGER NOT NULL," +
						 "level INTEGER NOT NULL," +
						 "lives INTEGER NOT NULL," +
						 "rockets INTEGER NOT NULL," +
						 "emp INTEGER NOT NULL," +
						 "last_saved DATETIME NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e) {
			if(!tableAlreadyExists(e.getMessage())) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}

	}
	
	private void createScoresTable() {

		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE scores (" +
						 "player_id INT NOT NULL," +
						 "level INT NOT NULL," +
						 "score INT NOT NULL," +
						 "date DATETIME NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e) {
			if(!tableAlreadyExists(e.getMessage())) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}

	}
	
	public boolean savePlayer(PlayerSave save) {
		
		boolean result = true;
		
		try {
			stmt = c.createStatement();
			String sql = "";
			if(save.player_id == 0) {
				sql = "INSERT INTO playersave (name, score, level, last_saved, lives, rockets, emp, player_id) VALUES ('"+save.name+"', "+save.score+", "+save.level+", datetime('now'), "+save.lives+", "+save.rockets+", "+save.emp+", "+save.player_id+")";
			}
			else {
				sql = "UPDATE playersave SET name = '"+save.name+"', score = "+save.score+", level = "+save.level+", last_saved = datetime('now'), lives = "+save.lives+", rockets = "+save.rockets+", emp = "+save.emp+", player_id = "+save.player_id+" WHERE playersave_id = "+save.playersave_id;
			}
						
			stmt.executeUpdate(sql);
			
		
			if(save.player_id == 0) {
				// Save player id
				ResultSet getLastID = stmt.getGeneratedKeys();
				if(getLastID.next()) {
					save.playersave_id = getLastID.getInt(1);
					System.out.println(save.player_id);
				}
			}
			
			ShooterGame.PLAYER_SAVE = save;
			
			stmt.close();
		
		}
		catch(Exception e) {
			System.out.println("Failed to save player info");
			System.out.println(e.getMessage());
			result = false;
		}
	
		return result;
	}
	
	
	public ArrayList<PlayerSave> getPlayerSaves() {
		
		ArrayList<PlayerSave> playerSaves = new ArrayList<PlayerSave>();
		PlayerSave tempSave;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");

		try {
			stmt = c.createStatement();
			String sql = "SELECT playersave_id, name, score, level, last_saved, rockets, lives, emp, player_id FROM playersave ORDER BY last_saved DESC";
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()) {
				tempSave = new PlayerSave();
				tempSave.playersave_id = result.getInt("playersave_id");
				tempSave.player_id = result.getInt("player_id");
				tempSave.name = result.getString("name");
				tempSave.score = result.getInt("score");
				tempSave.level = result.getInt("level");
				tempSave.date.setTime(df.parse(result.getString("last_saved")));
				tempSave.rockets = result.getInt("rockets");
				tempSave.emp = result.getInt("emp");
				tempSave.lives = result.getInt("lives");
				playerSaves.add(tempSave);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage()+" - error");
			JOptionPane.showMessageDialog(null, "Error accessing game saves.\n\nShutting down game.", ShooterGame.GAME_NAME, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		return playerSaves;
	}
	
	
	private boolean tableAlreadyExists(String errorMessage) {
		boolean tableExists = false;
		
		String alreadyExists = "already exists";
		
		if(errorMessage.substring(errorMessage.length()-alreadyExists.length()).equals(alreadyExists)) {
			tableExists = true;
		}
		
		return tableExists;
	}
	
}
