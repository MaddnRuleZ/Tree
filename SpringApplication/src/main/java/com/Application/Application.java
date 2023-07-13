package com.Application;

import com.Application.Interpreter.Parser;
import com.Application.Tree.elements.Root;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@SpringBootConfiguration
@ComponentScan(basePackages = {"com.Application"})

public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);



		boolean maddinInTheHouse = true;
		if (maddinInTheHouse) {
			// start test Suite, kannst darunter dein Test code machen, set false für einfaches ausschalten
			Parser parser = new Parser("SpringApplication/src/MaddinIsTesting/PSE_TEST_1.txt");
			Root root = parser.startParsing();

			String[] finishedTextReCompiled = root.toText();
			for (String str: finishedTextReCompiled) {
				System.out.println(str);
			}
		}

	}
}
