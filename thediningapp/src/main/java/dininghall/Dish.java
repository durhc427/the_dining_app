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
  private String kitchen;
  private String diningHall;
  private String mealTime;

	Dish(String n, Collection<String> a) {
		this.setName(n);
		this.allergies = a;
	}

  Dish(String n, String dh, String k, String mt) {
      this.setName(n);
      this.allergies = new HashSet<String>();
      this.diningHall = dh;
      this.mealTime = mt;
      this.kitchen = k;
  }

	public String getName() {
		return name;
	}

  public Collection<String> getAllergies(){
      return this.allergies;
  }

  public String getMealTime(){
      return this.mealTime;
  }

  public String getKitchen() {
      return this.kitchen;
  }

  public String getDiningHall() {
      return this.diningHall;
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
    json.put("hall", this.diningHall);
    json.put("mealtime", this.mealTime);

		for(String allergen : this.allAllergens){
			if(this.allergies.contains(allergen)){
				json.put(allergen, true);
			} else {
				json.put(allergen, false);
			}
		}

		return json;
	}
}
