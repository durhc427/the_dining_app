package com.dininghall.DiningAppApi;

import java.time.LocalDate;
import java.util.Collection;

import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class API {
	public static final String[] HALLS =  {"Everybody's Kitchen", "USC Village Dining Hall", "Parkside Restaurant & Grill"};
	public static final String[] halls = {"everybody", "village", "parkside"};


	@RequestMapping(value="/all", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAll() {


		LocalDate d = LocalDate.now();
		JSONObject json = new JSONObject();
		JSONArray dhdishes = new JSONArray();

		for(String s : HALLS){
			Collection<JSONObject> dishes = new DiningHall(s,d).toJSONCollection();
			for(JSONObject dish : dishes){
				dhdishes.add(dish);
			}
		}

		json.put("dishes", dhdishes);


		return json;
	}



        @RequestMapping(value="/dininghall", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
        public JSONObject getDiningHall(String dininghall){


            LocalDate d = LocalDate.now();
            DiningHall dh = new DiningHall(getHall(dininghall), d);
            return dh.toSimplifiedJSON();
        }



    @RequestMapping(value="/pulldate", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getDiningHallDate(String name, int year, int month, int day ){
        LocalDate d = LocalDate.of(year,month, day);
        String dininghall = getHall(name);
        DiningHall dh = new DiningHall(dininghall, d);
        return dh.toSimplifiedJSON();
    }


    @RequestMapping(value="/alldhdate", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getAllDiningHallsDate(int year, int month, int day) {
        LocalDate d = LocalDate.of(year, month, day);
        JSONObject json = new JSONObject();
	JSONArray dhdishes = new JSONArray();

	for(String s : HALLS){
		Collection<JSONObject> dishes = new DiningHall(s,d).toJSONCollection();
		for(JSONObject dish : dishes){
			dhdishes.add(dish);
		}
	}

	json.put("dishes", dhdishes);

        return json;
    }

    @RequestMapping(value="/login", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject loginUser(String name, String password) {
		JSONObject json = new JSONObject();
		User user = null;
		MySQL db = new MySQL();
		if(name != null && name.trim().length() > 0 && 
		password != null && password.trim().length() > 0
		&& db.getUser(name, password) > 0) {
			user = new User(name, password);
			json.put("loginStatus", true);
		} else {
			json.put("loginStatus", false);
		}
	    //json.put("registrationStatus", true);
	    return json;
    }

    @RequestMapping(value="/register", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject registerUser(String name, String password) {
		JSONObject json = new JSONObject();
		User user = null;
		MySQL db = new MySQL();
		if(name != null && name.trim().length() > 0 && 
		password != null && password.trim().length() > 0
		&& db.getUser(name, password) < 0){
			user = new User(name, password);
			user.addUser(name, password);
			json.put("registrationStatus", true);
		} else {
			json.put("registrationStatus", false);
		}
	    return json;
    }


    @RequestMapping(value="/logintestinvalid", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject loginUserInvalid(String name, String password) {
	    JSONObject json = new JSONObject();
	    // code to check if in database
	    json.put("loginStatus", false);

	    return json;
    }

    @RequestMapping(value="/registertestinvalid", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject registerUserInvalid(String name, String password) {
	    JSONObject json = new JSONObject();
	    // code to check if in database
	    json.put("registrationStatus", false);

	    return json;
    }

    @RequestMapping(value="/addfavorite", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject addDish(String name, String dish){

		JSONObject json = new JSONObject();
		User user = null;
		MySQL db = new MySQL();
		String tmp = db.findUser(name);
		if(name != null && name.trim().length() > 0
		&& dish != null && dish.trim().length() > 0
		&& tmp.length() > 0){
			user = new User(name, tmp);
			user.addDish(dish);
			json.put("addStatus", true);
		} else {
			json.put("addStatus", false);
		}
		
	    // make database call
	    // User.addDish(username, dish);
	    return json;
	}
	

    @RequestMapping(value="/adddish", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject addDish(String name, String password, String dish){

		JSONObject json = new JSONObject();
		User user = null;
		MySQL db = new MySQL();
		if(name != null && name.trim().length() > 0
		&& dish != null && dish.trim().length() > 0
		&& db.getUser(name, password) > 0){
			user = new User(name, password);
			user.addDish(dish);
			json.put("addStatus", true);
		}  else {
			json.put("addStatus", false);
		}
		
	    // make database call
	    // User.addDish(username, dish);
	    return json;
	}
	

    @RequestMapping(value="/adduserallergen", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject addAllergen(String name, String password, String allergen){

		JSONObject json = new JSONObject();
		User user = null;
		MySQL db = new MySQL();
		if(name != null && name.trim().length() > 0
		&& allergen != null && allergen.trim().length() > 0
		&& db.getUser(name, password) > 0){
			user = new User(name, password);
			user.addAllergen(allergen);
			json.put("addStatus", true);
		}  else {
			json.put("addStatus", false);
		}
		
	    // make database call
	    // User.addDish(username, dish);
	    return json;
	}
	

    @RequestMapping(value="/addallergen", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject addAllergen(String name, String allergen){

		JSONObject json = new JSONObject();
		User user = null;
		MySQL db = new MySQL();
		String tmp = db.findUser(name);

		if(name != null && name.trim().length() > 0
		&& allergen != null && allergen.trim().length() > 0
		&& tmp.length() > 0){
			user = new User(name, tmp);
			user.addAllergen(allergen);
			json.put("addStatus", true);
		}  else {
			json.put("addStatus", false);
		}
		
	    // make database call
	    // User.addDish(username, dish);
	    return json;
    }


    @RequestMapping(value="/getfavssafe", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getFavs(String name, String password){

	    JSONObject json = new JSONObject();
		User user = new User(name, password);
		JSONArray favorites = user.getDishes();
		json.put("dishes", favorites);
	    return json;
	}
	
	@RequestMapping(value="/getallergenssafe", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllergens(String name, String password){
	    JSONObject json = new JSONObject();
		User user = new User(name, password);
		JSONArray allergens = user.getAllergens();
		json.put("allergens", allergens);
	    return json;
	}

    @RequestMapping(value="/getfavs", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getFavs(String name){

		JSONObject json = new JSONObject();
		MySQL db = new MySQL();
		String tmp = db.findUser(name);
		User user = new User(name, tmp);
		JSONArray favorites = user.getDishes();
		json.put("dishes", favorites);
	    return json;
	}
	
	@RequestMapping(value="/getallergens", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllergens(String name){
	    JSONObject json = new JSONObject();
		MySQL db = new MySQL();
		String tmp = db.findUser(name);
		User user = new User(name, tmp);
		JSONArray allergens = user.getAllergens();
		json.put("allergens", allergens);
	    return json;
	}

    private String getHall(String name){
        for(int i = 0; i < halls.length ; i++){
            if(name.equals(halls[i])) return HALLS[i];
        }
        return "";
    }


}
