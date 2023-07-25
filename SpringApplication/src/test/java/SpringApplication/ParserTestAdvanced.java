package SpringApplication;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Interpreter.Parser;
import com.Application.Tree.elements.roots.Root;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTestAdvanced {

    @Test
    public void testParser() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/TestDocuments/PSE_TEST_2.txt");
        Root root = (Root) parser.startParsing();

        //TODO: Ich habe die to Latex-Methode angefangen
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0);
        System.out.println(map.get("root").toString());

    }
}