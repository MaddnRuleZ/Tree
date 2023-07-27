package com.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * main class of the application
 * starts the application and initializes the spring boot context
 */
@SpringBootApplication
@SpringBootConfiguration
@EnableScheduling
public class Application {

	@Autowired
	private User user;

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
}
