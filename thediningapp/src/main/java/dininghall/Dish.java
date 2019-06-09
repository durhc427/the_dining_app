package main.java.dininghall;

import java.util.Collection;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class Dish {
	private String name;
	private Collection<String> allergies;
	private String [] allAllergens = {"Dairy", "Eggs", "Fish",
	"Food Not Analyzed for Allergens", "Peanuts", "Pork", "Sesame",
	"Shellfish", "Soy", "Tree Nuts", "Vegan", "Vegetarian", "Wheat / Gluten"};

	Dish(String n, Collection<String> a) {
		this.setName(n);
		this.allergies = a;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	Dish(String n) {
		this.setName(n);
		this.allergies = new HashSet<String>();
	}

	void addAllergy(String allergy) {
		this.allergies.add(allergy);
	}

	JSONArray getAllergens() {
		JSONArray allergens = new JSONArray();
		return allergens;
	}

	Collection<String> getDishAllergens() {
		return this.allergies;
	}

	JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("name", this.getName());
		JSONObject allergens = new JSONObject();
		/*
		JSONArray allergens = new JSONArray();
		for (String allergen : this.allergies) {
			allergens.add(allergen);
		}
		*/
		for(String allergen : this.allAllergens){
			if(allergen.contains(allergen)){
				allergens.put(allergen, true);
			} else {
				allergens.put(allergen, false);
			}
		}

		json.put("allergies", allergens);
		return json;
	}

}
