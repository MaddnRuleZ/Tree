package com.Application;

import com.Application.Interpreter.Parser;
import com.Application.Printer.Clock;
import com.Application.Printer.Printer;
import com.Application.Tree.elements.Root;
import com.Application.Tree.interfaces.Roots;
import jakarta.annotation.PostConstruct;
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
		boolean setMeFALSE  = true;
		if (setMeFALSE) {
			// start test Suite, kannst darunter dein Test code machen, set false für einfaches ausschalten
			Parser parser = new Parser("SpringApplication/src/MaddinIsTesting/PSE_TEST_1.txt");
			Roots root = parser.startParsing();

			List<String> finishedTextReCompiled = root.toText();
			for (String str: finishedTextReCompiled) {
				System.out.println(str);
			}
		}
	}
}
