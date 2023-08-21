package SpringApplication.ParserTests;

import com.application.exceptions.FileInvalidException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ParserTest {
    @Test
    public void testParser() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_1.txt");
        Root root = null;

        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {

        }
        if (root == null) {
            System.out.println("Root null, document not correctly loaded");
            return;
        }

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }

    @Test
    public void testParserDemo() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/DemoFileText.tex");
        Root root = null;

        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {

        }
        if (root == null) {
            System.out.println("Root null, document not correctly loaded");
            return;
        }

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }

    @Test
    public void testParserAdvanced() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_1.txt");
        Root root = null;

        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {

        }
        if (root == null) {
            System.out.println("Root null, document not correctly loaded");
            return;
        }

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }

    @Test
    public void environmentTest() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_ENV.txt");
        Root root = null;

        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        }
        if (root == null) {
            System.out.println("Root null, document not correctly loaded");
            return;
        }

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }

    @Test
    public void parsingErrorTest() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/ErrorCatchingTest.tex");
        Root root = null;

        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        }
        if (root == null) {
            System.out.println("Root null, document not correctly loaded");
            return;
        }

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }


    @Test
    public void summaryTest() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/SummaryTestDocument.txt");
        Root root = null;

        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        }
        if (root == null) {
            System.out.println("Root null, document not correctly loaded");
            return;
        }

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }

    @Test
    public void startBlockTest() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/StartBlockText.tex");
        Root root = null;

        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        }
        if (root == null) {
            System.out.println("Root null, document not correctly loaded");
            return;
        }

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        root.toLaTeX(map, "root", 0, true, true);
        System.out.println(map.get("root").toString());
    }


    @Test
    public void doubleParserTest() throws FileInvalidException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/ENV_TST.txt");
        parser.initStartParsing();
        Root root = null;
        try {
            root = (Root) parser.startParsing();
        } catch (FileInvalidException e) {
            throw new RuntimeException(e);
        }

        Parser parserTwo = new Parser("src/test/resources/TestDocuments/PSE_TEST_1.txt");
        parserTwo.initStartParsing();

        root =  (Root) parserTwo.startParsing();
        System.out.println("spacer");
    }
}
