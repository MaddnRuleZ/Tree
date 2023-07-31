package SpringApplication.CommandTests.Execution;

import com.application.User;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.application.command.types.LoadFromFolderCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FilePrinterTest {
    String path = "src/test/java/SpringApplication/TestDocuments/PSE_TEST_1.txt";

    @BeforeEach
    void setUp() throws ParseException, UnknownElementException, IOException, FileInvalidException {
        LoadFromFolderCommand command = new LoadFromFolderCommand();
        User user = new User();
        Printer printer = new FilePrinter(path, user.getRoot());
        Parser parser = new Parser(path);
        boolean success = command.load(user, printer, path);

        assert success;

        printer.export();
    }

    @Test
    void export() {
        //TODO: implement
    }
}