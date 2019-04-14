package com.dininghall.DiningAppApi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MySQL {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public MySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Local
			//this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dining_app?user=root&password=root");
			// GCP
			this.conn = DriverManager.getConnection("jdbc:mysql://35.185.251.221:3306/Dining_App?user=root&password=toor");
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

	public void closeConnection() {
		try {
			if (rs != null) { this.rs.close(); }
			if (ps != null) { this.ps.close(); }
			if (conn != null) { this.conn.close(); }
		}
		catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		}
	}

	public int getdhID(String dhName) {
		int id = -1;
		try {
			String sql = "SELECT diningHallID FROM DiningHalls WHERE name=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, dhName);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("diningHallID");
			}
			sql = "INSERT INTO DiningHalls (name) VALUES (?);";

			this.ps = this.conn.prepareStatement(sql);

			this.ps.setString(1, dhName);

			this.ps.execute();

			sql = "SELECT diningHallID FROM DiningHalls WHERE name=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, dhName);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("diningHallID");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return id;
	}

	public int getmID(String mName) {
		int id = -1;
		try {
			String sql = "SELECT mealID FROM Meals WHERE name=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, mName);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("mealID");
			}
			sql = "INSERT INTO Meals (name) VALUES (?);";

			this.ps = this.conn.prepareStatement(sql);

			this.ps.setString(1, mName);

			this.ps.execute();

			sql = "SELECT mealID FROM Meals WHERE name=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, mName);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("mealID");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return id;
	}

	public int getkID(String kName, int dhID) {
		int id = -1;
		try {
			String sql = "SELECT kitchenID FROM Kitchens WHERE name=? AND diningHallID=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, kName);
			this.ps.setInt(2, dhID);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("kitchenID");
			}
			sql = "INSERT INTO Kitchens (name, diningHallID) VALUES (?, ?);";

			this.ps = this.conn.prepareStatement(sql);

			this.ps.setString(1, kName);
			this.ps.setInt(2, dhID);

			this.ps.execute();

			sql = "SELECT kitchenID FROM Kitchens WHERE name=? AND diningHallID=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, kName);
			this.ps.setInt(2, dhID);

			this.rs = ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("kitchenID");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return id;
	}

	public int getdID(String dName) {
		int id = -1;
		try {
			String sql = "SELECT dishID FROM Dishes WHERE name=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, dName);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("dishID");
			}
			sql = "INSERT INTO Dishes (name) VALUES (?);";

			this.ps = this.conn.prepareStatement(sql);

			this.ps.setString(1, dName);

			this.ps.execute();

			sql = "SELECT dishID FROM Dishes WHERE name=?;";
			this.ps = conn.prepareStatement(sql);
			this.ps.setString(1, dName);

			this.rs = this.ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("dishID");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return id;
	}

	public int getaID(String aName) {
		int id = -1;
		try {
			String sql = "SELECT allergenID FROM Allergens WHERE name=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, aName);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("allergenID");
			}
			sql = "INSERT INTO Allergens (name) VALUES (?);";

			this.ps = this.conn.prepareStatement(sql);

			this.ps.setString(1, aName);

			this.ps.execute();

			sql = "SELECT allergenID FROM Allergens WHERE name=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(1, aName);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return this.rs.getInt("allergenID");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return id;
	}

	public void addTodhDishes(int dID, int mID, int kID, LocalDate date) {
		try {
			Date sqlDate = java.sql.Date.valueOf(date);

			String sql = "SELECT diningHallDishesID "
						+ "FROM DiningHallDishes "
						+ "WHERE dishID=? "
							+ "AND mealID=? "
							+ "AND kitchenID=? "
							+ "AND dt=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setInt(1, dID);
			this.ps.setInt(2, mID);
			this.ps.setInt(3, kID);
			this.ps.setString(4, sqlDate.toString());

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return;
			}
			sql = "INSERT INTO DiningHallDishes (dishID, mealID, kitchenID, dt) "
					+ "VALUES (?, ?, ?, ?);";

			this.ps = this.conn.prepareStatement(sql);

			this.ps.setInt(1, dID);
			this.ps.setInt(2, mID);
			this.ps.setInt(3, kID);
			this.ps.setString(4, sqlDate.toString());

			this.ps.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public void addToDishAllergens(int aID, int dID) {
		try {
			String sql = "SELECT dishAllergenID "
						+ "FROM DishAllergens "
						+ "WHERE dishID=? "
							+ "AND allergenID=?;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setInt(1, dID);
			this.ps.setInt(2, aID);

			this.rs = this.ps.executeQuery();

			if (this.rs.next()) {
				return;
			}
			sql = "INSERT INTO DishAllergens (dishID, allergenID) "
					+ "VALUES (?, ?);";

			this.ps = this.conn.prepareStatement(sql);

			this.ps.setInt(1, dID);
			this.ps.setInt(2, aID);

			this.ps.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public ResultSet getDishes(String name, LocalDate date) {
		ResultSet res = null;

		Date sqlDate = java.sql.Date.valueOf(date);

		try {
			String sql = "SELECT d.name AS dName, m.name AS mName, "
							+ "k.name AS kName, a.name AS aName "
							+ "FROM DiningHallDishes dhd, DiningHalls dh, "
							+ "Dishes d, Kitchens k, Meals m, DishAllergens da, "
							+ "Allergens a "
    						+ "WHERE dhd.dishID=d.dishID "
							+ "AND dhd.mealID=m.mealID "
        					+ "AND k.diningHallID=dh.diningHallID "
        					+ "AND dhd.kitchenID=k.kitchenID "
        					+ "AND da.dishID=d.dishID "
        					+ "AND da.allergenID=a.allergenID "
        					+ "AND dt=? "
        					+ "AND dh.name=? "
							+ "ORDER BY m.name, k.name, d.name;";
			this.ps = this.conn.prepareStatement(sql);
			this.ps.setString(2, name);
			this.ps.setString(1, sqlDate.toString());

			res = ps.executeQuery();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return res;
	}

}
