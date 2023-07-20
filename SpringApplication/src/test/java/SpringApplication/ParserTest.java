package SpringApplication;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Interpreter.Parser;
import com.Application.Tree.elements.roots.Roots;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTest {
    @Test
    public void testParser() throws UnknownElementException {

        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("SpringApplication/src/TestDocuments/PSE_TEST_1.txt");
        Roots root = parser.startParsing();


        //TODO: Ich habe die to Latex-Methode angefangen
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root");
        System.out.println(map.get("root").toString());

    }
}
