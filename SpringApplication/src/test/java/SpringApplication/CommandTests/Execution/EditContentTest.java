package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.EditContentCommand;
import com.application.command.types.MoveElementEditorCommand;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.NumParamsException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.printer.FilePrinter;
import com.application.interpreter.Parser;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;
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
    String testPath = "src/test/resources/PrinterTestOutput/EditTest.txt";
    ComplexTestTree tree;
    EditContentCommand command;
    User user;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = ComplexTestTree.createTestTree();
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
        UUID random = tree.getRandomUsedUUID();
        Element element = tree.root.searchForID(random);
        String oldContent = element.getContent();

        String newContent = "newContent: " + randomString;
        command.setElement(element.getId());
        command.setContent(newContent);


        assertThrows(ParseException.class, () -> command.checkBadString(newContent), "Bad content should throw ParseException");


        FilePrinter printer = new FilePrinter(testPath, user);
        printer.export();
    }


    String randomString = "\\part{options_text}[content_test]\n" +
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
