package endpoints;
import dininghall.*;
import com.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import java.util.concurrent.CompletableFuture;

@Service
public class ImportService {
	private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

	// class imports data from given date into database

	@Async("asyncExecutor")
	public CompletableFuture<Boolean> importDate(LocalDate date)  throws InterruptedException {
		logger.info("importDate starts");
		boolean finished = true;
		DiningHallImporter dhi = new DiningHallImporter(date);
		Thread th = new Thread(dhi);
		logger.info("starting importer");
		th.start();
		logger.info("...waiting to finish import");
		while(th.isAlive()){

		}

		logger.info("...Thread is Dead");

		return CompletableFuture.completedFuture(finished);

	}


}
