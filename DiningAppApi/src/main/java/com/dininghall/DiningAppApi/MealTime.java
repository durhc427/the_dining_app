package com.dininghall.DiningAppApi;

import java.util.Collection;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class MealTime {
	private String name;
	private Collection<Kitchen> kitchens;

	MealTime(String n, Collection<Kitchen> kits) {
		this.setName(n);
		this.kitchens = kits;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	MealTime(String n) {
		this.setName(n);
		this.kitchens = new HashSet<Kitchen>();
	}

	void addKitchen(Kitchen k) {
		this.kitchens.add(k);
	}

	JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("name", this.getName());
		JSONArray ks = new JSONArray();
		for (Kitchen k : this.kitchens) {
			ks.add(k.toJSON());
		}
		json.put("kitchens", ks);
		return json;
	}

	public Collection<Kitchen> getKitchens() {
		return this.kitchens;
	}

}
