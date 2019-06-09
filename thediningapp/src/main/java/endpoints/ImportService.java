package main.java.endpoints;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import main.java.com.DiningHallImporter;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import main.java.dininghall.DiningHall;

@Service
public class ImportService {
	private static final Logger logger = LoggerFactory.getLogger(ImportService.class);
	public static final String[] HALLS = { "Everybody's Kitchen", "USC Village Dining Hall",
			"Parkside Restaurant & Grill" };
	// class imports data from given date into database

	@Async("asyncExecutor")
	public CompletableFuture<Boolean> importDate(LocalDate date) throws InterruptedException {
		logger.info("importDate starts");
		boolean finished = true;
		DiningHallImporter dhi = new DiningHallImporter(date);
		Thread th = new Thread(dhi);
		logger.info("starting importer");
		th.start();
		logger.info("...waiting to finish import");
		while (th.isAlive()) {

		}
		logger.info("...Thread is Dead");
		return CompletableFuture.completedFuture(finished);

	}

	@Async("asyncExecutor")
	public CompletableFuture<Object> importSupplier(LocalDate date) throws InterruptedException, ExecutionException {
		logger.info("importSupplier starts");
		DiningHallImporter dhi = new DiningHallImporter(date);
		Thread th = new Thread(dhi);
		logger.info("starting importer");
		th.start();
		logger.info("...waiting to finish import");
		CompletableFuture<JSONObject> hall_1 = CompletableFuture.supplyAsync(() -> {
				logger.info("hall_1 caller");
				if(date.getDayOfMonth() - 7 > 0){
					DiningHall hall = new DiningHall(HALLS[0], LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth() - 7));
					return hall.toSimplifiedJSON();
				}  else {
					DiningHall hall = new DiningHall(HALLS[0], date);
					return hall.toSimplifiedJSON();

				}

		});


		CompletableFuture<JSONObject> hall_2 = CompletableFuture.supplyAsync(() -> {
				logger.info("hall_2 caller");
				if(date.getDayOfMonth() - 7 > 0){
					DiningHall hall = new DiningHall(HALLS[1], LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth() - 7));
					return hall.toSimplifiedJSON();
				}  else {
					DiningHall hall = new DiningHall(HALLS[0], date);
					return hall.toSimplifiedJSON();

				}

		});
		CompletableFuture<Object> obj = CompletableFuture.anyOf(hall_1, hall_2);
		return CompletableFuture.completedFuture(obj).get();

	}


}
