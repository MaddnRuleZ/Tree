package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.DeleteElementCommand;
import com.application.command.types.ExportCommand;
import com.application.exceptions.ParseException;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExportTest {
    TestTree tree;
    ExportCommand command;
    User user;
    FilePrinter printer;
    File fileToDelete = new File("src/test/resources/PrinterTestOutput/ExportTest.txt");

    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        user = new User();
        user.setRoot(tree.root);
        printer = new FilePrinter("src/test/resources/PrinterTestOutput/ExportTest.txt", user);
        command = new ExportCommand(printer);
        command.setUser(user);
    }

    @Test
    public void exportTest() {
        command.execute();

        assertTrue(fileToDelete.exists(), "File should exist");

    }


    @AfterEach
    public void tearDown() {
        tree = null;
        command = null;
        user = null;
        printer = null;
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }
}
