package SpringApplication.CommandTests.Generation;

import SpringApplication.TestStubs.MockCommandHandler;
import SpringApplication.TestStubs.TestTree;
import com.application.Exceptions.ProcessingException;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests the creation of commands with a correct formatted jsonFile
 */
@Ignore("This test is old")
public class CorrectCommandTest {
    MockCommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setRoot(new TestTree().root);
        commandHandler = new MockCommandHandler(user);
    }

    @Test
    void AddElementTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/AddElement.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }
    @Test
    void DeleteElementTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/DeleteElement.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }

    @Test
    void EditCommentTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditComment.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }

    @Test
    void EditContentTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditContent.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }
    @Test
    void EditSummaryTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/EditSummary.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }
    @Test
    void LoadFromFolderTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/LoadFromFolder.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }
    @Test
    void LoadFromGitTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/LoadFromGit.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }
    @Test
    void MoveElementTreeTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/MoveElementTree.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
    }

    @Test
    void MoveElementEditorTest() throws ProcessingException {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/MoveElementEditor.json");
        assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        assertTrue(commandHandler.isSuccess(), "Success should be true");
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