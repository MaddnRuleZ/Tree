package SpringApplication.CommandTests.Execution;

import com.application.User;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.application.command.types.LoadFromFolderCommand;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FilePrinterTest {
    String pathOfTestDocument = "src/test/resources/TestDocuments/PSE_TEST_1.txt";
    String pathOfPrinter = "src/test/resources/PrinterTestOutput/PSE_TEST_1_FilePrinter.txt";
    Printer printer;

    @BeforeEach
    void setUp() throws FileInvalidException {
        Parser parser = new Parser(pathOfTestDocument);
        User user = new User();
        Root root = (Root) parser.startParsing();
        user.setRoot(root);
        this.printer = new FilePrinter(pathOfPrinter, user);
    }

    @Test
    void exportTest() throws UnknownElementException, IOException {
        printer.export();
    }

    @AfterEach
    void tearDown() {
        printer = null;
    }


}