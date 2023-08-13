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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetCommandTest {

    TestTree tree;
    GetCommand command;
    User user;
    FilePrinter printer;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        user = new User();
        user.setRoot(tree.root);
    }

    @ParameterizedTest
    @CsvSource({
            "true, src/test/resources/TestDocuments/SuccessResponseEditor.json",
            "false, src/test/resources/TestDocuments/SuccessResponseTree.json"})
    public void getTest(boolean isEditor, String expected) throws IOException {
        command = new GetCommand(user, isEditor);
        JsonNode response = command.execute();

        String expectedString = Files.readString(Path.of(expected));

        assertEquals(expectedString, response.toString(), "Should be equal");
    }

    @Test
    public void failureResponseTest() {
        command = new GetCommand(user, true);
        command.getUser().setRoot(null);
        JsonNode response = command.execute();

        assertEquals("{\"error\":\"Fehler beim Generieren der Antwort. Es wird empfohlen das Programm zu beenden und das LaTeX-Dokument manuell zu überprüfen.\"}", response.toString(), "Should be equal");
    }
}
