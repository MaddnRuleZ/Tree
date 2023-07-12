package SpringApplication.CommandTests;

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
 * tests the commands with a correct formatted jsonFile
 */
public class CorrectCommandTest {
    CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        commandHandler = new CommandHandler(new User());
    }

    @Test
    void AddElementTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/AddElement.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }
    @Test
    void DeleteElementTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/DeleteElement.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }

    @Test
    void EditCommentTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditComment.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }

    @Test
    void EditContentTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditContent.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }
    @Test
    void EditSummaryTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditSummary.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }
    @Test
    void LoadFromFolderTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/LoadFromFolder.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }
    @Test
    void LoadFromGitTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/LoadFromGit.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
    }
    @Test
    void MoveElementTest() throws NumParamsException, UnrecognizedCommandException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/MoveElement.json");
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