package SpringApplication.CommandTests.Generation;

import com.application.command.factories.*;
import com.application.exceptions.ProcessingException;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests the creation of commands with a correct formatted jsonFile
 */
public class CorrectGenerationTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void AddElementTest() throws ProcessingException {
        CommandFactory factory = new AddCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/AddElement.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }
    @Test
    void DeleteElementTest() throws ProcessingException {
        CommandFactory factory = new DeleteCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/DeleteElement.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }

    @Test
    void EditCommentTest() throws ProcessingException {
        CommandFactory factory = new EditCommentCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/EditComment.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }

    @Test
    void EditContentTest() throws ProcessingException {
        CommandFactory factory = new EditContentCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/EditContent.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }

    @Test
    void EditSummaryTest() throws ProcessingException {
        CommandFactory factory = new EditSummaryCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/EditSummary.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }
    @Test
    void LoadFromFolderTest() throws ProcessingException {
        CommandFactory factory = new LoadFromFolderCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/LoadFromFolder.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }
    @Test
    void LoadFromGitTest() throws ProcessingException {
        CommandFactory factory = new LoadFromGitCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/LoadFromGit.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }
    @Test
    void MoveElementTreeTest() throws ProcessingException {
        CommandFactory factory = new MoveElementTreeCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/MoveElementTree.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
    }

    @Test
    void MoveElementEditorTest() throws ProcessingException {
        CommandFactory factory = new MoveElementEditorCommandFactory(new User());
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectAttributes/MoveElementEditor.json");
        assertNotNull(factory.createCommand(jsonContent), "Response should not be null");
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

