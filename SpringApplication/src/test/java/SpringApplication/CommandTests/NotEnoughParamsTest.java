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
 * tests the commands with one missing parameter in the jsonFile
 */
public class NotEnoughParamsTest {
    CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        commandHandler = new CommandHandler(new User());
    }

    @Test
    void AddElementTest(){
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/AddElement.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in AddElement", exception.getMessage());
    }
    @Test
    void DeleteElementTest(){
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/DeleteElement.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in DeleteElement", exception.getMessage());
    }

    @Test
    void EditCommentTest(){
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/EditComment.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in EditComment", exception.getMessage());
    }

    @Test
    void EditContentTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/EditContent.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in EditContent", exception.getMessage());
    }
    @Test
    void EditSummaryTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/EditSummary.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in EditSummary", exception.getMessage());
    }
    @Test
    void LoadFromFolderTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/LoadFromFolder.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in LoadFromFolder", exception.getMessage());
    }
    @Test
    void LoadFromGitTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/LoadFromGit.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in LoadFromGit", exception.getMessage());
    }
    @Test
    void MoveElementEditorTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/MoveElementEditor.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in MoveElementEditor", exception.getMessage());
    }

    @Test
    void MoveElementTreeTest() {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/NotEnoughParamsCommands/MoveElementTree.json");
        boolean success = false;
        Throwable exception = assertThrows(NumParamsException.class, () -> commandHandler.processCommand(jsonContent, success));
        assertEquals("Missing Parameter in MoveElementTree", exception.getMessage());
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
