package com.a3_soccer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class A3SoccerApplication {

	public static void main(String[] args) {
		SpringApplication.run(A3SoccerApplication.class, args);
	}

}
