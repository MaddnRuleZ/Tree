package SpringApplication;

import com.application.Exceptions.UnknownElementException;
import com.application.Interpreter.Parser;
import com.application.Tree.elements.roots.Root;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTest {
    @Test
    public void testParser() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/TestDocuments/PSE_TEST_1.txt");
        Root root = (Root) parser.startParsing();

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0);
        System.out.println(map.get("root").toString());
    }
}
