package SpringApplication.CommandTests.Execution;

import com.application.User;
import com.application.command.types.LoadFromFolderCommand;
import com.application.exceptions.ProcessingException;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the execution of an LoadFromFolderCommand
 */
public class LoadFolderTest {
    /**
     * Command to test
     */
    LoadFromFolderCommand command;
    /**
     * User on which the test is executed on
     */
    User user;

    /**
     * Printer to print the tree
     */
    Printer printer;


    /**
     * Sets up the test environment before each test
     */
    @BeforeEach
    void setUp() {
        this.user = new User();
        this.command = new LoadFromFolderCommand();
        this.command.setUser(user);
    }

    /**
     * Tests the execution of an LoadFromFolderCommand
     * Tests if the content is added correctly
     */
    @Test
    void DoubleLoadTest() throws ProcessingException {
        String path = "src/test/resources/TestDocuments/PSE_TEST_1.txt";
        this.command.setPath(calculatePath(path));
        command.execute();
        assertTrue(command.isSuccess(), "Command should execute successfully");

        Root firstRoot = user.getRoot();
        Printer firstPrinter = user.getPrinter();

        this.command.setPath(calculatePath(path));
        command.execute();
        assertTrue(command.isSuccess(), "Command should execute successfully");

        Root sndRoot = user.getRoot();
        Printer sndPrinter = user.getPrinter();

        assertNotEquals(firstRoot, sndRoot, "Roots should not be equal");
        assertNotEquals(firstPrinter, sndPrinter, "Printers should not be equal");
    }

    /**
     * Tests the execution of an LoadFromFolderCommand
     * Tests if the content is added correctly
     */
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/LoadTestPaths/WrongType.jpg",
                            "src/test/resources/LoadTestPaths",
                            "src/test/resources/LoadTestPaths/NotExistingFile.tex"})
    public void wrongFilePath(String path) {
        this.command.setPath(calculatePath(path));
        command.execute();
        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }

    /**
     * Tests the execution of an LoadFromFolderCommand
     * Tests if the content is added correctly
     */
    @Test
    public void parseExceptionTest() {
        this.command.setPath("src/test/resources/LoadTestPaths/NotParsableTest.txt");
        command.execute();
        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }

    @AfterEach
    void tearDown() {
        user = null;
        command = null;
        printer = null;

    }

    private String calculatePath(String path) {
        File file = new File(path);
        return file.getAbsolutePath();
    }
}
