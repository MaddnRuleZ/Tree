package SpringApplication.AdvancedParserTest;

import com.application.exceptions.FileInvalidException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;

import java.util.HashMap;
import java.util.Map;

public class ParserTestErrors {
    public void testParserErrors() throws UnknownElementException, FileInvalidException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/java/SpringApplication/TestDocuments/PSE_TEST_ERROR_TESTING.tex");
        Root root = (Root) parser.startParsing();

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0);
        System.out.println(map.get("root").toString());
    }
}
