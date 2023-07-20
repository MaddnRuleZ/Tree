package SpringApplication.SpringTests;

import com.Application.Command.CommandHandler;
import com.Application.Exceptions.NumParamsException;
import com.Application.Exceptions.UnrecognizedCommandException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests unrecognizable Commands
 */
public class CommandHandlerTest {
    CommandHandler commandHandler;
    JsonNode response;

    @BeforeEach
    void setUp() {
        commandHandler = new CommandHandler(new User());
        response = null;
    }

    @Test
    void UnrecognizedCommandExceptionTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/UnknownCommandJson.json");
        boolean success = false;
        Throwable exception = assertThrows(UnrecognizedCommandException.class, () -> commandHandler.processCommand(jsonContent));
        assertEquals("Element", exception.getMessage());
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
