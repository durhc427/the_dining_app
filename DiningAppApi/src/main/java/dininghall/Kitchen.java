package dininghall;

import java.util.Collection;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class Kitchen {
	private String name;
	private Collection<Dish> dishes;

	Kitchen(String n, Collection<Dish> d) {
		this.name = n;
		this.dishes = d;
	}

	Kitchen(String n) {
		this.name = n;
		this.dishes = new HashSet<Dish>();
	}

	void addDish(Dish d) {
		this.dishes.add(d);
	}

	public Collection<Dish> getDishes() {
		return this.dishes;
	}

	JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("name", this.name);
		JSONArray ds = new JSONArray();
		for (Dish dish : this.dishes) {
			ds.add(dish.toJSON());
		}
		json.put("dishes", ds);
		return json;
	}
}
