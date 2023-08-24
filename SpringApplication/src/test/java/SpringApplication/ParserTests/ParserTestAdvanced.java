package SpringApplication.ParserTests;

import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTestAdvanced {

    @Test
    public void testParser() throws UnknownElementException, FileInvalidException, ParseException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_1.txt");
        Root root = (Root) parser.startParsingText();

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }

    @AfterEach
    public void resetPrinter() {
        Root.resetInstance();
    }
}