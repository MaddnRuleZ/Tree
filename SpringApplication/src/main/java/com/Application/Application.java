package com.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		TreeX treeX = new TreeX();

		SpringApplication.run(Application.class, args);
	}

}
