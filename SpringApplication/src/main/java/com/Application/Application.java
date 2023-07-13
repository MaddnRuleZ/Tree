package com.Application;

import com.Application.Interpreter.Parser;
import com.Application.Tree.elements.sectioning.Root;
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
		/* TODO tests bitte unter test/java/com/SpringApplication schreiben als JUNit Testklassen nicht in das Programm
		 und benutze vielleicht etwas aussagekräftigere Namen für die Tests damit wir die dann auch in der Qualitätssicherung einfach mitabgeben können
		 und nicht erst nochmal umbenennen müssen
		 */
		boolean maddinInTheHouse = true;

		if (maddinInTheHouse) {
			// start test Suite, kannst darunter dein Test code machen, set false für einfaches ausschalten
			Parser parser = new Parser("SpringApplication/src/MaddinIsTesting/PSE_TEST_2.txt");
			Root root = parser.startParsing();


			String[] finishedTextReCompiled = root.toTextAdvanced();
			for (String str: finishedTextReCompiled) {
				System.out.println(str);
			}


		}





	}
}
