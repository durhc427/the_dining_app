package com.dininghall.DiningAppApi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;

//import data.MySQL;

public class User {
	private int userID;
	private MySQL mysql;

	public User(String uname, String pword) {
		this.mysql = new MySQL();
        this.userID = this.mysql.getUser(uname, pword);
	}

	public static void addUser(String uname, String pword) {
		MySQL mysql = new MySQL();

		mysql.addUser(uname, pword);
	}

	public void addDish(String dName) {
		this.mysql.addFavorite(this.userID, dName);
	}

	public void addAllergen(String aName) {
		this.mysql.addAllergen(userID, aName);
	}

	public JSONArray getAllergens() {
		JSONArray json = new JSONArray();

		ResultSet rs = this.mysql.getAllergens(this.userID);
		try {
		while (rs.next()) {
			json.add(rs.getString("name"));
		}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return json;
	}

	public JSONArray getDishes() {
		JSONArray json = new JSONArray();
		ResultSet rs = this.mysql.getFavorites(this.userID);
		try {
		while (rs.next()) {
			json.add(rs.getString("name"));
		}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return json;		
	}
}