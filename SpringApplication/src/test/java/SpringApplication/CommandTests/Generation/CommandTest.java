package SpringApplication.CommandTests.Generation;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.MockCommandHandler;
import SpringApplication.TestStubs.TestTree;
import com.application.command.factories.*;

import com.application.exceptions.NumParamsException;
import com.application.exceptions.ParseException;
import com.application.exceptions.ProcessingException;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests the creation of commands with a correct formatted jsonFile
 */
public class CommandTest {
    private MockCommandHandler commandHandler;

    @BeforeEach
    public void setUp() {
        TestTree testTree = new ComplexTestTree();
        User user = new User();
        user.setRoot(testTree.root);
        this.commandHandler = new MockCommandHandler(user);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/JsonFiles/CorrectCommands/AddElement.json",
            "src/test/resources/JsonFiles/CorrectCommands/DeleteElement.json",
            "src/test/resources/JsonFiles/CorrectCommands/MoveElementTree.json",
            "src/test/resources/JsonFiles/CorrectCommands/MoveElementEditor.json",
            "src/test/resources/JsonFiles/CorrectCommands/EditComment.json",
            "src/test/resources/JsonFiles/CorrectCommands/EditContent.json",
            "src/test/resources/JsonFiles/CorrectCommands/EditSummary.json",
            "src/test/resources/JsonFiles/CorrectCommands/LoadFromFolder.json",
            "src/test/resources/JsonFiles/CorrectCommands/LoadFromGit.json"
    })
    void testCorrectCommand(String path) {
        JsonNode jsonContent = loadJsonFile(path);
        try {
            assertNotNull(commandHandler.processCommand(jsonContent), "Response should not be null");
        } catch (ProcessingException e) {
            fail(e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("wrongJsonFileTestData")
    void testWrongCommand(CommandFactory factory) {
        JsonNode jsonContent = loadJsonFile("src/test/resources/JsonFiles/WrongParams.json");
        assertThrows(NumParamsException.class, () -> factory.createCommand(jsonContent));
    }

    static Stream<Arguments> wrongJsonFileTestData() {
        return Stream.of(
                Arguments.of(new LoadFromFolderCommandFactory(new User())),
                Arguments.of(new LoadFromGitCommandFactory(new User())),
                Arguments.of(new AddCommandFactory(new User())),
                Arguments.of(new DeleteCommandFactory(new User())),
                Arguments.of(new EditCommentCommandFactory(new User())),
                Arguments.of(new EditContentCommandFactory(new User())),
                Arguments.of(new EditSummaryCommandFactory(new User())),
                Arguments.of(new MoveElementEditorCommandFactory(new User())),
                Arguments.of(new MoveElementTreeCommandFactory(new User()))
        );
    }




    @AfterEach
    public void tearDown() {
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