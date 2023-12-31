package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.EditContentCommand;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.printer.FilePrinter;
import com.application.interpreter.Parser;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests the execution of editContent commands
 */
public class EditContentTest {
    /**
     * path to the test file
     */
    final String testPath = "src/test/resources/PrinterTestOutput/EditTest.txt";
    /**
     * TestTree on which the test is executed on
     */
    ComplexTestTree tree;
    /**
     * Command to test
     */
    EditContentCommand command;
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
        command = new EditContentCommand();
        user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }

    /**
     * tests the execution of a editContent command on a Sectioning Element
     * with a suitable content
     */
    @Test
    public void editContentSectioningTest() {
        Sectioning sec = tree.sectioningList.get(3);
        String oldContent = sec.getContent();
        String newContent = "newContent";

        command.setElement(sec.getId());
        command.setContent(newContent);
        command.execute();

        assertEquals(newContent, sec.getContent(), "Content should be changed");
        assertNotEquals(oldContent, sec.getContent(), "Content should be changed");
    }

    /**
     * tests the execution of a editContent command on a Child Element
     * with a bad content, consisting of several lines of LaTeX-Code
     * tests if export() works correctly
     */
    @Test
    public void editWithBadContent() throws UnknownElementException, IOException {
        Element element = tree.childrenList.get(6);
        String oldContent = element.getContent();

        String newContent = "newContent: " + randomString;
        command.setElement(element.getId());
        command.setContent(newContent);


        assertThrows(ParseException.class, () -> command.checkBadString(newContent), "Bad content should throw ParseException");


        FilePrinter printer = new FilePrinter(testPath, user);
        printer.export();
    }

    /**
     * tests if an exception is thrown if the element is not found
     */
    @Test
    public void ElementNotFoundTest() {
        command.setElement(tree.notUsedUUID);
        command.setContent("newContent");
        command.execute();

        assertFalse(command.isSuccess(), "Command should fail");
    }

    /**
     * tests if an exception is thrown if the content is null
     */
    @Test
    public void emptyContentTest() {
        Sectioning sec = tree.sectioningList.get(3);
        String oldContent = sec.getContent();
        String newContent = "";

        command.setElement(sec.getId());
        command.setContent(newContent);
        command.execute();

        assertNotEquals(newContent, sec.getContent(), "Content should not be changed");
        assertEquals(oldContent, sec.getContent(), "Content should not be changed");
    }

    /**
     * tests the execution if the element is on the first level
     */
    @Test
    public void firstLevelTest() {
        Sectioning sec = tree.sectioningList.get(0);
        String newContent = "newContent";
        String oldContent = sec.getContent();

        command.setElement(sec.getId());
        command.setContent(newContent);
        command.execute();

        assertEquals(newContent, sec.getContent(), "Content should be changed");
        assertNotEquals(oldContent, sec.getContent(), "Content should be changed");
    }

    /**
     * resets the test environment after each test
     */
    @AfterEach
    public void cleanUp() {
        tree = null;
        command = null;
        user = null;
    }


    final String randomString = "\\part{options_text}[content_test]\n" +
            "    X1\n" +
            "    X\n" +
            "    % comment1\n" +
            "    % comment2\n" +
            "    X\n" +
            "    X\n" +
            "    %\\start{summary}\n" +
            "        X\n" +
            "        X\n" +
            "        X\n" +
            "    %\\finish{summary}\n" +
            "    X\n" +
            "    X\n" +
            "    X\n" +
            "    \\par\n" +
            "    X1_1\n" +
            "    X\n" +
            "    X\n" +
            "    X\n" +
            "    \\begin{figure}\n" +
            "        X1_2\n" +
            "        X\n" +
            "        X\n" +
            "        X\n" +
            "        \\newline\n" +
            "        X1_3\n" +
            "        X\n" +
            "        X\n" +
            "        \\includegraphics{testgraph1}[moreStuff]\n" +
            "        \\caption{captionToAdd}\n" +
            "        \\label\n" +
            "        X\n" +
            "        X\n" +
            "    \\end{figure}";


}
