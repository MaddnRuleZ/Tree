package SpringApplication.ParserTests;

import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.interpreter.Scanner;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_2.txt");
        Root root = null;

        try {
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException | ParseException e) {

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
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException e) {

        } catch (ParseException e) {
            throw new RuntimeException(e);
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
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
    public void doubleParserTest() throws FileInvalidException, ParseException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/MoveTEST.txt");
        parser.initStartParsing();
        Root root = null;
        try {
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Parser parserTwo = new Parser("src/test/resources/TestDocuments/PSE_TEST_1.txt");
        parserTwo.initStartParsing();

        root =  (Root) parserTwo.startParsingText();
        System.out.println("spacer");
    }

    @Test
    public void addTestExternCase() throws UnknownElementException, ParseException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/advancedTest.tex");
        Root root = null;

        try {
            root = (Root) parser.startParsingText();
        } catch (FileInvalidException e) {
            System.out.println("Invalid File Used");
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
}
