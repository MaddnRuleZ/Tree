package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.ExportCommand;
import com.application.command.types.GetCommand;
import com.application.exceptions.ParseException;
import com.application.printer.FilePrinter;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the execution of an ExportCommand
 */
public class GetCommandTest {

    /**
     * TestTree on which the test is executed on
     */
    TestTree tree;
    /**
     * Command to test
     */
    GetCommand command;
    /**
     * User on which the test is executed on
     */
    User user;

    /**
     * Sets up the test environment before each test
     * @throws ParseException if the testTree could not be created
     */
    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        user = new User();
        user.setRoot(tree.root);
    }

    /**
     * Tests the execution of an ExportCommand
     * Tests if the content is added correctly
     */
    @ParameterizedTest
    @CsvSource({
            "true, editor",
            "false, tree"})
    public void getTest(boolean isEditor, String expected) throws IOException {
        command = new GetCommand(user, isEditor);
        JsonNode response = command.execute();
        Iterator fieldNames = response.fieldNames();

        assertEquals(expected, fieldNames.next() , "Should be equal");
    }

    /**
     * Tests if an exception is thrown if the root is null
     */
    @Test
    public void failureResponseTest() {
        command = new GetCommand(user, true);
        command.getUser().setRoot(null);
        JsonNode response = command.execute();

        assertEquals("{\"error\":\"Fehler beim Generieren der Antwort. Es wird empfohlen das Programm zu beenden und das LaTeX-Dokument manuell zu überprüfen.\"}", response.toString(), "Should be equal");
    }
}
