package SpringApplication;

import com.Application.Command.CommandHandler;
import com.Application.Exceptions.NumParamsException;
import com.Application.Exceptions.UnrecognizedCommandException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests recognizing and parsing of incoming jsonFiles to the CommandHandler
 */
public class CommandHandlerJUnitTest {
    CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        commandHandler = new CommandHandler();
    }

    @Test
    void UnrecognizedCommandExceptionTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/UnknownCommandJson.json");
        Throwable exception = assertThrows(UnrecognizedCommandException.class, () -> commandHandler.processCommand(jsonContent));
        assertEquals("Element", exception.getMessage());
    }

    @Test
    void NotEnoughParamExceptionTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamJson.json");
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
        assertEquals("Missing Parameter in AddCommand", exception.getMessage());
    }

    @Test
    void AddElementTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/AddElementJson.json");
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
