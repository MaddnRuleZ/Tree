package SpringApplication;

import com.application.exceptions.FileInvalidException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CombinedParserAndLaTeXParserTest {

    Root root;


    @BeforeEach
    public void setUp() throws FileInvalidException {
        Parser parser = new Parser("src/test/java/SpringApplication/TestDocuments/PSE_TEST_1.txt");
        root = (Root) parser.startParsing();
    }

    @Test
    public void testLaTeXParser() throws UnknownElementException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL);
        for(String key : map.keySet()) {
            assert key != null;
            if(key.equals("root")) {
                String output = map.get(key).toString();

                System.out.println(output);
            }
        }
    }
}
