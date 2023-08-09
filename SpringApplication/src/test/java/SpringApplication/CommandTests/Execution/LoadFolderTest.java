package SpringApplication.CommandTests.Execution;

import com.application.command.CommandHandler;
import com.application.exceptions.ProcessingException;
import com.application.User;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoadFolderTest {
    CommandHandler commandHandler;
    User user;
    String path = "src/test/resources/JsonFiles/LoadFromFolderTest_1.json";

    @BeforeEach
    void setUp() {
        this.user = new User();
        commandHandler = new CommandHandler(user);
    }

    @Test
    void DoubleLoadTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile(path);
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");

        Root firstRoot = user.getRoot();
        Printer firstPrinter = user.getPrinter();

        JsonNode newJsonContent = loadJsonFile(path);
        assertNotNull(commandHandler.processCommand(newJsonContent), "Response should not be null");

        Root sndRoot = user.getRoot();
        Printer sndPrinter = user.getPrinter();

        assertNotEquals(firstRoot, sndRoot, "Roots should not be equal");
        assertNotEquals(firstPrinter, sndPrinter, "Printers should not be equal");

    }

    private JsonNode loadJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
