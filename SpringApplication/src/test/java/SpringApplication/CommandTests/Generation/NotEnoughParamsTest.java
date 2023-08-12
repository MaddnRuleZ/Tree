package SpringApplication.CommandTests.Generation;

import com.application.command.CommandHandler;
import com.application.exceptions.NumParamsException;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests the commands with one missing parameter in the jsonFile
 */
public class NotEnoughParamsTest {
    CommandHandler commandHandler;
    JsonNode response;

    @BeforeEach
    void setUp() {
        response = null;
        commandHandler = new CommandHandler(new User());
    }

    @Test
    void AddElementTest(){
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/AddElement.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }
    @Test
    void DeleteElementTest(){
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/DeleteElement.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }

    @Test
    void EditCommentTest(){
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/EditComment.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }

    @Test
    void EditContentTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/EditContent.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }
    @Test
    void EditSummaryTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/EditSummary.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }
    @Test
    void LoadFromFolderTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/LoadFromFolder.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }
    @Test
    void LoadFromGitTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/LoadFromGit.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }
    @Test
    void MoveElementEditorTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/MoveElementEditor.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }

    @Test
    void MoveElementTreeTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/MoveElementTree.json");
        assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent));
    }

    @AfterEach
    void tearDown() {
        commandHandler = null;
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
