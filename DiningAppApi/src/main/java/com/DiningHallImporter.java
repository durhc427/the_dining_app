package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import data.MySQL;

public class DiningHallImporter implements Runnable {
	private LocalDate date;
	private JSONArray res = null;
	private static final String scraperURI = "https://us-central1-menuscraper.cloudfunctions.net/scrape_date?date=";

	public DiningHallImporter(LocalDate d) {
		this.date = d;
	}

	public void run() {
		this.res = getFromScraper();
		this.importToDatabase();
	}

	private JSONArray getFromScraper() {

		JSONArray json = null;

		System.out.println("calling scraper");	// added

		StringBuilder sb = new StringBuilder(scraperURI);
		try {
			sb.append(date.getYear());
			sb.append(date.getMonthValue());
			sb.append(date.getDayOfMonth());
			URL url = new URL(sb.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
			    sb.append(line);
			}
			in.close();
			con.disconnect();

			JSONParser p = new JSONParser();
			String jsonString = sb.toString();
			json = (JSONArray)p.parse(jsonString);
		} catch (MalformedURLException me) {
			System.out.println("MUE : " + me.getMessage());
			me.printStackTrace();
		} catch (IOException ie) {
			System.out.println("IOE : " + ie.getMessage());
			ie.printStackTrace();
		} catch (ParseException pe) {

			System.out.println("PE : " + pe.getMessage());
			pe.printStackTrace();
		}
		return json;
	}

	private void importToDatabase() {
		MySQL msql = new MySQL();

		System.out.println("calling import to database"); // added

		for (int i = 0; i < this.res.size(); i++) {
			JSONObject json = (JSONObject)this.res.get(i);
			int dhID = msql.getdhID((String)json.get("dining_hall"));
			int mID = msql.getmID((String)json.get("meal"));
			JSONArray dishes = (JSONArray)json.get("dishes");
			for (int j = 0; j < dishes.size(); j++) {
				JSONObject dish = (JSONObject)dishes.get(j);
				int kID = msql.getkID((String)dish.get("kitchen"), dhID);
				int dID = msql.getdID((String)dish.get("dish_name"));
				msql.addTodhDishes(dID, mID, kID, this.date);
				JSONArray allergens = (JSONArray)dish.get("dietary_tags");
				for (int k = 0; k < allergens.size(); k++) {
					String allergen = (String)allergens.get(k);
					int aID = msql.getaID(allergen);
					msql.addToDishAllergens(aID, dID);
				}
			}
		}
		msql.closeConnection();
	}
}
