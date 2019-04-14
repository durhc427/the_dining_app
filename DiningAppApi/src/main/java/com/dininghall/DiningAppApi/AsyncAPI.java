package com.dininghall.DiningAppApi;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import java.time.LocalDate;
import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AsyncAPI {

	public static final String[] HALLS =  {"Everybody's Kitchen", "USC Village Dining Hall", "Parkside Restaurant & Grill"};
	public static final String[] halls = {"everybody", "village", "parkside"};

	@Autowired
	private ImportService service;
/*
	@RequestMapping(value="/testAsync", method=RequestMethod.GET)
	public void testAsync() throws InterruptedException, ExecutionException {
		System.out.println("testAsync start");
		LocalDate date = LocalDate.of(2019,05,02);
		DiningHall dh = new DiningHall("village", date);
		System.out.println("DiningHall Results Before");
		System.out.println(dh.toJSON().toString());
		CompletableFuture<Boolean>  menus = service.importDate(date);
		CompletableFuture.allOf(menus).join();
		System.out.println("testAsync end");
		System.out.println("DiningHall Results After");
		DiningHall dh2 = new DiningHall("village", date);
		System.out.println(dh2.toJSON().toString());
	}



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

	*/
}
