package SpringApplication.CommandTests.Execution;

import com.application.command.CommandHandler;
import com.application.exceptions.ProcessingException;
import com.application.User;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoadTest {
    CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        commandHandler = new CommandHandler(new User());
    }

    @Test
    void LoadFromFolderTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/Execution/LoadFromFolder");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }

    @Test
    void DoubleLoadTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/Execution/LoadFromFolder");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");

        Root firstRoot = Root.getInstance();

        JsonNode neWJsonContent = loadJsonFile("src/test/resources/JsonFiles/Execution/LoadFromFolder");
        assertNotNull(commandHandler.processCommand(neWJsonContent), "Response should not be null");

        Root sndRoot = Root.getInstance();

        assertNotEquals(firstRoot, sndRoot, "Roots should not be equal");
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
