package SpringApplication;

import com.Application.Interpreter.Parser;
import com.Application.Tree.interfaces.Roots;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ParserTest {


    @Test
    public void testParser() {

        Parser parser = new Parser("SpringApplication/src/MaddinIsTesting/PSE_TEST_2.txt");
        Roots root = parser.startParsing();

        List<String> finishedTextReCompiled = root.toText();
        for (String str: finishedTextReCompiled) {
            System.out.println(str);
        }
    }
}
