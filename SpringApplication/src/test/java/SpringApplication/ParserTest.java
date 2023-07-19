package SpringApplication;

import com.Application.Interpreter.Parser;
import com.Application.Tree.interfaces.Roots;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ParserTest {
    @Test
    public void testParser() {

        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("SpringApplication/src/TestDocuments/PSE_TEST_1.txt");
        Roots root = parser.startParsing();

        List<String> finishedTextReCompiled = root.toText();
        for (String str: finishedTextReCompiled) {
            System.out.println(str);
        }
    }
}
