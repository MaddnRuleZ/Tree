package com.Application;

import com.Application.Interpreter.Parser;
import com.Application.Printer.Clock;
import com.Application.Tree.interfaces.Roots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

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
