package main.java.com.Application;

import main.java.com.Application.Command.CommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		TreeX treeX = new TreeX();
		CommandHandler commandHandler = new CommandHandler(treeX);
		SpringApplication.run(Application.class, args);
	}

}
