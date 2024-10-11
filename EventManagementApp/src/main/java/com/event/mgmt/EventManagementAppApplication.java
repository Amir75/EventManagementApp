package com.event.mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class EventManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementAppApplication.class, args);
	}

}
