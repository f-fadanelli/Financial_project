package com.felipfad.fin_project;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling
@SpringBootApplication
public class FinProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinProjectApplication.class, args);
	}

}
