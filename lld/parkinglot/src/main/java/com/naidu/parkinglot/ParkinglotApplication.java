package com.naidu.parkinglot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ParkinglotApplication {

	@Autowired
	private static ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(ParkinglotApplication.class, args);
	}

}
