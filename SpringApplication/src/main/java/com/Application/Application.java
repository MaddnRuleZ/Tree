package com.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
