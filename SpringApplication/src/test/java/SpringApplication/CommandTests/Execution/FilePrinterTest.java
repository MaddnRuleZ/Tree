package SpringApplication.CommandTests.Execution;

import com.application.User;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.PrintException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.application.command.types.LoadFromFolderCommand;
import com.application.tree.elements.childs.Child;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Input;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import SpringApplication.TestStubs.TestTree;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FilePrinterTest {
    String pathOfPrinter = "src/test/resources/PrinterTestOutput/PSE_TEST_1_FilePrinter.txt";
    Printer printer;
    User user;
    TestTree tree;

    @BeforeEach
    void setUp() throws FileInvalidException, ParseException {
        this.user = new User();
        this.tree = TestTree.createTestTree();
        tree.root.setMinLevel(1);
        this.user.setRoot(tree.root);
        this.printer = new FilePrinter(pathOfPrinter, user);
    }

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

    @Test
    public void wrongInputPath() {
        Input input = new Input();
        input.setContentManually(null);

        tree.root.addChildOnIndex(0, input);
        input.setParent(null);

        assertThrows(UnknownElementException.class, () -> {
            printer.export();
        });
    }

    @AfterEach
    void tearDown() {
        printer = null;
        user = null;
        tree = null;
    }


}