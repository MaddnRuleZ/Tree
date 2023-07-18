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
		LOGO();

		Parser parser = new Parser("SpringApplication/src/TestDocuments/PSE_TEST_1.txt");
		Roots root = parser.startParsing();

		List<String> finishedTextReCompiled = root.toText();
		for (String str: finishedTextReCompiled) {
			System.out.println(str);
		}
	}

	public static void LOGO() {
		System.out.println("___________                     ____  ___");
		System.out.println("\\__    ___/______   ____   ____ \\   \\/  /");
		System.out.println("  |    |  \\_  __ \\_/ __ \\_/ __ \\ \\     / ");
		System.out.println("  |    |   |  |  /\\  ___/\\  ___/ /     \\ ");
		System.out.println("  |____|   |__|    \\___  >\\___  >___/\\  \\");
		System.out.println("                       \\/     \\/      \\_/");
	}
}
