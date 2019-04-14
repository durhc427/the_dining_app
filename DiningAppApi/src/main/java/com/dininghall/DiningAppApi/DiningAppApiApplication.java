package com.dininghall.DiningAppApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@EnableScheduling
public class DiningAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiningAppApiApplication.class, args);
	}
}
