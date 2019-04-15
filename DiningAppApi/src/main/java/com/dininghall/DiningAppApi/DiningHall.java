package com.dininghall.DiningAppApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import data.MySQL;

public class DiningHall {
	private Collection<MealTime> mtimes;
	private LocalDate date;
	private String name;
	private String [] allAllergens = {"Dairy", "Eggs", "Fish",
	"Food Not Analyzed for Allergens", "Peanuts", "Pork", "Sesame",
	"Shellfish", "Soy", "Tree Nuts", "Vegan", "Vegetarian", "Wheat / Gluten"};

	public DiningHall(String n, LocalDate d) {
		this.name = n;
		this.date = d;
		this.mtimes = getMealTimes(this.name, this.date);
	}

	public Collection<JSONObject> toJSONCollection() {
		Collection<JSONObject> dishes = new ArrayList<JSONObject>();

		for (MealTime mt : this.mtimes){
			for(Kitchen kt : mt.getKitchens()) {
				for(Dish dh : kt.getDishes()){
					JSONObject dish = new JSONObject();
					for(String allergens : allAllergens){
						if(dh.getDishAllergens().contains(allergens)){
							dish.put(allergens, true);
						} else {
							dish.put(allergens, false);
						}
					}
					dish.put("name", dh.getName());
					dish.put("mealtime", mt.getName());
					dish.put("hall", this.name);
					dishes.add(dish);
				}
			}
		}

		return dishes;

	}

	public JSONObject toSimplifiedJSON(){
		JSONObject json = new JSONObject();
		JSONArray dishes = new JSONArray();
		for (MealTime mt : this.mtimes){
			for(Kitchen kt : mt.getKitchens()) {
				for(Dish dh : kt.getDishes()){
					JSONObject dish = new JSONObject();
					for(String allergens : allAllergens){
						if(dh.getDishAllergens().contains(allergens)){
							dish.put(allergens, true);
						} else {
							dish.put(allergens, false);
						}
					}
					dish.put("name", dh.getName());
					dish.put("mealtime", mt.getName());
					dish.put("hall", this.name);
					dishes.add(dish);
				}
			}
		}
		json.put("dishes", dishes);
		//json.put("hall", this.name);
		return json;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("name", this.name);
		JSONArray mts = new JSONArray();
		for (MealTime mt : this.mtimes) {
			mts.add(mt.toJSON());
		}
		json.put("mealtimes", mts);
		return json;
	}

	private Collection<MealTime> getMealTimes(String name, LocalDate date) {
		MySQL mysql = new MySQL();

		ResultSet rs = mysql.getDishes(name, date);

		Collection<MealTime> mealtimes = new HashSet<MealTime>();

		String mName = null;
		MealTime m = null;
		String kName = null;
		Kitchen k = null;
		String dName = null;
		Dish d = null;

		try {
			while (rs.next()) {
				if (rs.getString("mName").equals(mName)) {
					if (rs.getString("kName").equals(kName)) {
						if (rs.getString("dName").equals(dName)) {
							d.addAllergy(rs.getString("aName"));
						} else {
							if (d != null) {
								k.addDish(d);
							}
							dName = rs.getString("dName");
							d = new Dish(dName);
							d.addAllergy(rs.getString("aName"));
						}
					} else {
						if (k != null) {
							m.addKitchen(k);
						}
						dName = rs.getString("dName");
						d = new Dish(dName);
						kName = rs.getString("kName");
						k = new Kitchen(kName);
						d.addAllergy(rs.getString("aName"));
					}
				} else {
					if (k != null) {
						m.addKitchen(k);
					}
					if (m != null) {
						mealtimes.add(m);
					}
					mName = rs.getString("mName");
					m = new MealTime(mName);
					kName = rs.getString("kName");
					k = new Kitchen(kName);
					dName = rs.getString("dName");
					d = new Dish(dName);
					d.addAllergy(rs.getString("aName"));
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		mysql.closeConnection();
		return mealtimes;
	}
}
