package main.java.endpoints;

import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.dininghall.DiningHall;
import main.java.dininghall.MealTime;

@RestController
public class AsyncAPI {

    public static final String[] HALLS = { "Everybody's Kitchen", "USC Village Dining Hall",
            "Parkside Restaurant & Grill" };
    public static final String[] halls = { "everybody", "village", "parkside" };
    
    public static final String[] MEALS = { "Breakfast", "Brunch", "Lunch" , "Dinner"};

    @Autowired
    private ImportService service;

    @RequestMapping(value = "/importdate", method = RequestMethod.GET)
    public void importDate(int year, int month, int day) throws InterruptedException, ExecutionException {
        System.out.println("importing date to system");
        LocalDate date = LocalDate.of(year, month, day);
        CompletableFuture<Boolean> menus = service.importDate(date);
        CompletableFuture.allOf(menus).join();
    }

    @RequestMapping(value = "/testpull", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getDiningHallDate(int year, int month, int day) throws InterruptedException, ExecutionException {
        LocalDate d = LocalDate.of(year, month, day);
        String dininghall = "USC Village Dining Hall";
        DiningHall dh = new DiningHall(dininghall, d);
        if (dh.noData()) {
            CompletableFuture<Boolean> menus = service.importDate(d);
            CompletableFuture.allOf(menus).join();
            DiningHall dht = new DiningHall(dininghall, d);
            return dht.toSimplifiedJSON();
        }
        return dh.toSimplifiedJSON();
    }

    @RequestMapping(value = "/testpulldate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<JSONObject> pullData(int year, int month, int day)
            throws InterruptedException, ExecutionException {
        LocalDate d = LocalDate.of(year, month, day);
        CompletableFuture<Object> obj = service.importSupplier(d);
        return (CompletableFuture<JSONObject>) obj.get();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getAll() throws InterruptedException, ExecutionException {

        LocalDate d = LocalDate.now();
        JSONObject json = new JSONObject();
        JSONArray dhdishes = new JSONArray();

        DiningHall dh = new DiningHall(HALLS[0], d);
        if (dh.noData()) {
            CompletableFuture<Boolean> done = service.importDate(d);
            CompletableFuture.allOf(done).join();

            for (String s : HALLS) {
                Collection<JSONObject> dishes = new DiningHall(s, d).toJSONCollection();
                for (JSONObject dish : dishes) {
                    dhdishes.add(dish);
                }
            }
        } else {
            dh = null;
            for (String s : HALLS) {
                Collection<JSONObject> dishes = new DiningHall(s, d).toJSONCollection();
                for (JSONObject dish : dishes) {
                    dhdishes.add(dish);
                }
            }
        }
        json.put("dishes", dhdishes);

        return json;
    }

    @RequestMapping(value = "/dininghall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getDiningHall(String dininghall) throws InterruptedException {

        LocalDate d = LocalDate.now();
        DiningHall dh = new DiningHall(getHall(dininghall), d);
        if (dh.noData()) {
            CompletableFuture<Boolean> menus = service.importDate(d);
            CompletableFuture.allOf(menus).join();
            dh = new DiningHall(dininghall, d);
        }
        return dh.toSimplifiedJSON();
    }

    @RequestMapping(value = "/pulldate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getDiningHallDate(String name, int year, int month, int day) throws InterruptedException {
        LocalDate d = LocalDate.of(year, month, day);
        String dininghall = getHall(name);
        DiningHall dh = new DiningHall(dininghall, d);
        if (dh.noData()) {
            CompletableFuture<Boolean> menus = service.importDate(d);
            CompletableFuture.allOf(menus).join();
            dh = new DiningHall(dininghall, d);
        }
        return dh.toSimplifiedJSON();
    }

    @RequestMapping(value = "/formatTest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getFormatTest(int year, int month, int day) throws InterruptedException {
        LocalDate d = LocalDate.of(year, month, day);
        System.out.println("Pulling data for " + d.toString());
        JSONObject json = new JSONObject();

        MealTime m0 = new MealTime("Breakfast", d);
        MealTime m1 = new MealTime("Brunch", d);
        MealTime m2 = new MealTime("Lunch", d);
        MealTime m3 = new MealTime("Dinner", d);

        MealTime[] meals = { m0, m1, m2, m3 };

        for (MealTime meal : meals) {
            if (meal.hasData()) {
                json.put(meal.getName(), meal.toJSONArray());
            }
        }
        return json;

    }

    @RequestMapping(value = "/alldhdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getAllMealTimesDate(int year, int month, int day) throws InterruptedException {
        LocalDate d = LocalDate.of(year, month, day);
        System.out.println("Pulling data for " + d.toString());
        JSONObject json = new JSONObject();
        JSONArray dhdishes = new JSONArray();

        MealTime m0 = new MealTime("Breakfast", d);
        MealTime m1 = new MealTime("Brunch", d);
        MealTime m2 = new MealTime("Lunch", d);
        MealTime m3 = new MealTime("Dinner", d);

        if (!m0.hasData() && !m1.hasData() && !m2.hasData() && !m3.hasData()) {
            System.out.println("No data in database");
            CompletableFuture<Boolean> done = service.importDate(d);
            CompletableFuture.allOf(done).join();
            
            for (String s : MEALS){
                MealTime meal = new MealTime(s, d);
                if(meal.hasData()){
                    json.put(meal.getName(), meal.toJSONArray());
                }
            }
        } else {
            MealTime[] meals = { m0, m1, m2, m3 };

            for (MealTime meal : meals) {
                if (meal.hasData()) {
                    json.put(meal.getName(), meal.toJSONArray());
                }
            }
        }

        return json;
    }

    @RequestMapping(value = "/alldhdatebydh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject getAllDiningHallsDate(int year, int month, int day) throws InterruptedException {
        LocalDate d = LocalDate.of(year, month, day);
        System.out.println("Pulling data for " + d.toString());
        JSONObject json = new JSONObject();
        JSONArray dhdishes = new JSONArray();

        DiningHall dh0 = new DiningHall(HALLS[0], d);
        DiningHall dh1 = new DiningHall(HALLS[1], d);
        DiningHall dh2 = new DiningHall(HALLS[2], d);
        if (dh0.noData() && dh1.noData() && dh2.noData()) {
            System.out.println("No data in database");
            CompletableFuture<Boolean> done = service.importDate(d);
            CompletableFuture.allOf(done).join();

            for (String s : HALLS) {
                Collection<JSONObject> dishes = new DiningHall(s, d).toJSONCollection();
                for (JSONObject dish : dishes) {
                    dhdishes.add(dish);
                }
            }
        } else {
            dh0 = null;
            dh1 = null;
            dh2 = null;

            for (String s : HALLS) {
                Collection<JSONObject> dishes = new DiningHall(s, d).toJSONCollection();
                for (JSONObject dish : dishes) {
                    dhdishes.add(dish);
                }
            }
        }
        json.put("dishes", dhdishes);

        return json;
    }

    // END NEW PULLING FUNCTIONS

    private String getHall(String name) {
        for (int i = 0; i < halls.length; i++) {
            if (name.equals(halls[i]))
                return HALLS[i];
        }
        return "";
    }

}
