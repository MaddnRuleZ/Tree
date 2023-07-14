package SpringApplication.CommandTests;

import SpringApplication.TestStubs.TestRoot;
import com.Application.Command.CommandHandler;
import com.Application.Exceptions.ProcessingException;
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
        User user = new User();
        user.setRoot(new TestRoot());
        commandHandler = new CommandHandler(user);

    }

    @Test
    void AddElementTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/AddElement.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
    }
    @Test
    void DeleteElementTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/DeleteElement.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
    }

    @Test
    void EditCommentTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditComment.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
        assertTrue(success, "Success should be true");
    }

    @Test
    void EditContentTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditContent.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
        assertTrue(success, "Success should be true");
    }
    @Test
    void EditSummaryTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditSummary.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
        assertTrue(success, "Success should be true");
    }
    @Test
    void LoadFromFolderTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/LoadFromFolder.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
    }
    @Test
    void LoadFromGitTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/LoadFromGit.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
    }
    @Test
    void MoveElementTreeTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/MoveElementTree.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
    }

    @Test
    void MoveElementEditorTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/MoveElementEditor.json");
        boolean success = false;
        assertNotNull(commandHandler.processCommand(jsonContent, success), "Response should not be null");
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