package SpringApplication.ParserTests;

import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 */
public class ParserTest {
    Root root;

    @BeforeEach
    public void init() {
        Root.resetInstance();
        root = Root.getInstance();
    }

    @Test
    public void testParserSelfmadeTestDocument() throws UnknownElementException, FileInvalidException, ParseException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_1.txt");
        Root.resetInstance();

        root = (Root) parser.startParsingText();
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
    public void testParserOurDocument() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_2.txt");
        root = null;

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
    public void cuustomTestCaseScenario() throws UnknownElementException, ParseException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/advancedTest.tex");
        root = null;

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
    public void testParserDemo() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/DemoFileText.tex");
        root = null;

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
        root = null;

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
        root = null;

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
        root = null;

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
        root = null;

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
    public void tmp() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/tmp2.tex");
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
    public void tmp3() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */
        Parser parser = new Parser("src/test/resources/TestDocuments/inputBForStartDoc.tex");
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

    @AfterEach
    public void reset() {
        Root.resetInstance();
    }
}
