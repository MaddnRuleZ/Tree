package com.Application;

import com.Application.Printer.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * main class of the application
 * starts the application and initializes the spring boot context
 */
@SpringBootApplication
@SpringBootConfiguration
@ComponentScan(basePackages = {"com.Application"})
public class Application {

	@Autowired
	private User user;
	private Clock clock;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
