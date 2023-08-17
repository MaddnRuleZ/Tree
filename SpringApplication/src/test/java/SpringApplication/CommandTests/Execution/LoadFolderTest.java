package SpringApplication.CommandTests.Execution;

import com.application.command.CommandHandler;
import com.application.command.types.LoadFromFolderCommand;
import com.application.exceptions.ProcessingException;
import com.application.User;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LoadFolderTest {
    LoadFromFolderCommand command;
    User user;
    String path = "src/test/resources/TestDocuments/PSE_TEST_1.txt";

    @BeforeEach
    void setUp() {
        this.user = new User();
        this.command = new LoadFromFolderCommand();
        this.command.setUser(user);
    }

    @Test
    void DoubleLoadTest() throws ProcessingException {
        this.command.setPath(path);
        command.execute();
        assertTrue(command.isSuccess(), "Command should execute successfully");

        Root firstRoot = user.getRoot();
        Printer firstPrinter = user.getPrinter();

        this.command.setPath(path);
        command.execute();
        assertTrue(command.isSuccess(), "Command should execute successfully");

        Root sndRoot = user.getRoot();
        Printer sndPrinter = user.getPrinter();

        assertNotEquals(firstRoot, sndRoot, "Roots should not be equal");
        assertNotEquals(firstPrinter, sndPrinter, "Printers should not be equal");
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/LoadTestPaths/WrongType.jpg",
                            "src/test/resources/LoadTestPaths",
                            "src/test/resources/LoadTestPaths/NotExistingFile.tex"})
    public void wrongFilePath(String path) {
        this.command.setPath(path);
        command.execute();
        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }

    @Test
    public void parseExceptionTest() {
        this.command.setPath("src/test/resources/LoadTestPaths/NotParsableTest.txt");
        command.execute();
        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }

    @AfterEach
    void tearDown() {
        user = null;
    }
}
