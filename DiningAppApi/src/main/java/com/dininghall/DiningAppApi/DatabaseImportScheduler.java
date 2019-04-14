package com.dininghall.DiningAppApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import java.time.LocalDate;

@Component
public class DatabaseImportScheduler {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseImportScheduler.class);
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Scheduled(cron = "0 * * * * ?")
	public void importFromDatabase() {
		System.out.printf("Cron Task Execution Time - %s", dtf.format(LocalDateTime.now()) );
		LocalDate date = LocalDate.now();
	}

}

