package com.dininghall.DiningAppApi;

import java.time.LocalDate;
import java.util.Collection;

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

            if(dh == null){
                return new JSONObject();
            }
            return dh.toSimplifiedJSON();
        }



    @RequestMapping(value="/pulldate", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getDiningHallDate(String name, int year, int month, int day ){
        LocalDate d = LocalDate.of(year,month, day);
        String dininghall = getHall(name);
        DiningHall dh = new DiningHall(dininghall, d);
        if(dh == null){
            return new JSONObject();
        }
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

    @RequestMapping(value="/login", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject loginUser(String name, String password) {
	    JSONObject json = new JSONObject();
	    // code to check if in database
	    json.put("loginStatus", true);

	    return json;
    }

    @RequestMapping(value="/register", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject registerUser(String name, String password) {
	    JSONObject json = new JSONObject();
	    // code to check if in database
	    json.put("registrationStatus", true);

	    return json;
    }


    @RequestMapping(value="/logintestinvalid", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject loginUserInvalid(String name, String password) {
	    JSONObject json = new JSONObject();
	    // code to check if in database
	    json.put("loginStatus", false);

	    return json;
    }

    @RequestMapping(value="/registertestinvalid", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject registerUserInvalid(String name, String password) {
	    JSONObject json = new JSONObject();
	    // code to check if in database
	    json.put("registrationStatus", false);

	    return json;
    }

    private String getHall(String name){
        for(int i = 0; i < halls.length ; i++){
            if(name.equals(halls[i])) return HALLS[i];
        }
        return "";
    }


}
