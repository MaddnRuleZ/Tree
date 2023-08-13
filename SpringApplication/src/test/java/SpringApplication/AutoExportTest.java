package SpringApplication;

import SpringApplication.TestStubs.ComplexTestTree;
import com.application.User;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutoExportTest {

    Printer printer;
    String path = "src/test/resources/PrinterTestOutput/AutoExportTest.txt";

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setRoot(new ComplexTestTree().root);


    }

    @Test
    void testAutoExport() {
    }
}
