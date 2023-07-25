package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.MockCommandHandler;
import SpringApplication.TestStubs.TestTree;
import com.Application.Command.CommandHandler;
import com.Application.Exceptions.ProcessingException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

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

    private JsonNode loadJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
