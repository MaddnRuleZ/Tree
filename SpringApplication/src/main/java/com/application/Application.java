package com.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * main class of the application
 * starts the application and initializes the spring boot context
 */
@SpringBootApplication
@SpringBootConfiguration
@EnableScheduling
public class Application {

	static int DEFAULT_PORT = 4200;

	/**
	 * starts the application and initializes the spring boot context
	 * @param args
	 */
	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
		Tools.openBrowserInstance();
	}
}
