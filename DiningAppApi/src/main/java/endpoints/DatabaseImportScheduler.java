package main.java.endpoints;
import com.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import main.java.dininghall.DiningHall;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

@Component
public class DatabaseImportScheduler {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseImportScheduler.class);
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

	// scheduled every day at midnight
	// @Scheduled(cron = "0 * * * * ?")
	// @Scheduled(cron = "0 0 0 * * ? ")
	// public void importFromDatabase() {
	// 	logger.info("Cron Task Execution Time - {}", dtf.format(LocalDateTime.now()));
	// 	// LocalDate date = LocalDate.now();
	// 	LocalDate date = LocalDate.of(2019, 04, 14);
	// 	DiningHall dhi = new DiningHall(date);
	// 	Thread th = new Thread(dhi);
	// 	th.start();
	// 	logger.info("Cron Task Finished Time - {}", dtf.format(LocalDateTime.now()) );
	// }
/*

	@Scheduled(cron = "0 0 7 * * ? ")
	public void importFromDatabase() {
		logger.info("Cron Task Execution Time - {}", dtf.format(LocalDateTime.now()) );
		//LocalDate date = LocalDate.now();
		LocalDate date = LocalDate.of(2019,04,14);
		DiningHallImporter dhi = new DiningHallImporter(date);
		Thread th = new Thread(dhi);
		th.start();
		logger.info("Cron Task Finished Time - {}", dtf.format(LocalDateTime.now()) );
	}
*/


}

