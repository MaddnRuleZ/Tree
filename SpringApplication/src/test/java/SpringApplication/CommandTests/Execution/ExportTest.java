package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.ExportCommand;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.printer.FilePrinter;
import com.application.tree.interfaces.LaTeXTranslator;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the execution of an ExportCommand
 */
public class ExportTest {
    /**
     * TestTree on which the test is executed on
     */
    TestTree tree;
    /**
     * Command to test
     */
    ExportCommand command;
    /**
     * User on which the test is executed on
     */
    User user;
    /**
     * Printer to print the tree
     */
    FilePrinter printer;


    /**
     * Sets up the test environment before each test
     * @throws ParseException if the testTree could not be created
     */
    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        user = new User();
        user.setRoot(tree.root);
        printer = new FilePrinter("src/test/resources/PrinterTestOutput/ExportTest.txt", user);
        user.setPrinter(printer);

        command = new ExportCommand();

        command.setExportComment(true);
        command.setExportSummary(true);
        command.setUser(user);

    }


    /**
     * Tests the execution of an ExportCommand wih different parameters for comment and summary
     */
    @ParameterizedTest
    @CsvSource({"true, false", "false, false", "true, true"})
    public void exportTestWithoutCommentSummary(boolean comment, boolean summary) throws UnknownElementException {

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        user.getRoot().toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, comment, summary);
        String output = map.get("root").toString();

        if(comment) {
            assertTrue(output.contains("%"), "Should contain comment");
        } else {
            assertTrue(!output.contains("%"), "Should not contain comment");
        }

        if(summary) {
            assertTrue(output.contains("\\start{summary}"), "Should contain summary");
        } else {
            assertTrue(!output.contains("\\start{summary}"), "Should not contain summary");
        }

    }


    /**
     * resets the test environment after each test
     */
    @AfterEach
    public void tearDown() {
        tree = null;
        command = null;
        user = null;
        printer = null;
    }
}
