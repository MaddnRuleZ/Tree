package com.Application;

import com.Application.Printer.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * main class of the application
 * starts the application and initializes the spring boot context
 */
@SpringBootApplication
@SpringBootConfiguration
public class Application {

	@Autowired
	private User user;
	private Clock clock;


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200") // Or you can use "*" to allow all origins
						.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") // Or you can use "*" to allow all methods
						.allowedHeaders("Content-Type", "Authorization") // Or you can use "*" to allow all headers
						.allowCredentials(true);
			}
		};
	}
	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
}
