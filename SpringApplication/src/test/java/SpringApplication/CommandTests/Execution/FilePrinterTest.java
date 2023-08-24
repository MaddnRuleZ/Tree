package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.OverleafGitException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Input;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilePrinterTest {
    /**
     * Path to the test file
     * */
    String pathOfPrinter = "src/test/resources/PrinterTestOutput/PSE_TEST_1_FilePrinter.txt";
    /**
     * Printer to print the tree
     */
    Printer printer;
    /**
     * User on which the test is executed on
     */
    User user;
    /**
     *  TestTree on which the test is executed on
     */
    TestTree tree;

    /**
     * Sets up the test environment before each test
     * @throws FileInvalidException if the testTree could not be created
     * @throws ParseException if the testTree could not be created
     */
    @BeforeEach
    void setUp() throws FileInvalidException, ParseException {
        this.user = new User();
        this.tree = TestTree.createTestTree();
        tree.root.setMinLevel(1);
        this.user.setRoot(tree.root);
        this.printer = new FilePrinter(pathOfPrinter, user);
    }

    /**
     * Tests if the export is aborted if an element is unknown
     */
    @Test
    void UnknownElement() {
        Sectioning sec = tree.sectioningList.get(tree.sectioningList.size()-1);
        Sectioning newSec = new Sectioning("UnknownElement", sec.getLevel() + 1);

        sec.getChildren().add(newSec);
        newSec.setParent(sec);

        assertThrows(UnknownElementException.class, () -> {
            printer.export();
        });
    }

    /**
     * Tests if the export is aborted if the path is wrong
     */
    @Test
    public void wrongInputPath() throws UnknownElementException, IOException, OverleafGitException {
        Input input = new Input();
        input.setContentManually(null);

        tree.root.addChildOnIndex(0, input);
        Root.getInstance().addChildOnIndex(0, input);
        input.setParent(null);
        assertThrows(UnknownElementException.class, () -> printer.export());

    }

    @AfterEach
    void tearDown() {
        printer = null;
        user = null;
        tree = null;
    }


}