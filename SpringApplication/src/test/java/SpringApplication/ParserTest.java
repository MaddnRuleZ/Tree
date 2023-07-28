package SpringApplication;

import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTest {
    @Test
    public void testParser() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/java/SpringApplication/TestDocuments/PSE_TEST_1.txt");
        Root root = (Root) parser.startParsing();

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0);
        System.out.println(map.get("root").toString());
    }
}
