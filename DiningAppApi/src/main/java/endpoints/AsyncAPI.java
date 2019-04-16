package endpoints;
import dininghall.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
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

	@RequestMapping(value="importdate",method=RequestMethod.GET )
	public void importDate(int year, int month, int day)  throws InterruptedException, ExecutionException {
		System.out.println("importing date to system");
		LocalDate date = LocalDate.of(year, month, day);
		CompletableFuture<Boolean> menus = service.importDate(date);
		CompletableFuture.allOf(menus).join();
	}


    @RequestMapping(value="/testpulldate", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public JSONObject pullData(int year, int month, int day)  throws InterruptedException, ExecutionException 	{
		LocalDate d = LocalDate.of(year,month, day);
		String dininghall = "USC Village Dining Hall";
		DiningHall dh = new DiningHall(dininghall, d);
		if( dh.noData() ) {

			System.out.println("importing date to system");
			LocalDate date = LocalDate.of(year, month, day);
			CompletableFuture<Boolean> menus = service.importDate(date);
			CompletableFuture.allOf(menus).join();
			dh = null;
			dh = new DiningHall(dininghall, d);
		}

		return dh.toSimplifiedJSON();
	}


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

	*/
}
