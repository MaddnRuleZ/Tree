package SpringApplication.ParserTests;

import SpringApplication.TestStubs.ComplexTestTree;
import com.application.User;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaTeXParserTest {

    @Test
    public void parseTest() throws UnknownElementException {
        ComplexTestTree tree = new ComplexTestTree();
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        tree.root.toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, true, true);
        System.out.println(map.get("root").toString());
    }

    @Test
    public void BeginOnSection() throws UnknownElementException {
        Parser parser = new Parser("src/test/resources/TestDocuments/BeginOnSection.tex");
        User user = new User();

        try {
            Root root = (Root) parser.startParsing();
            user.setRoot(root);
        } catch (FileInvalidException e) {
            e.printStackTrace();
        }
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        user.getRoot().toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, true, true);
        System.out.println(map.get("root").toString());

        assertEquals("\\section", user.getRoot().getChildren().get(0).getStartPart(), "Should be section");
    }

    @Test
    public void EnvTest() throws UnknownElementException {
        Parser parser = new Parser("src/test/resources/TestDocuments/PSE_TEST_ENV.txt");
        User user = new User();

        try {
            Root root = (Root) parser.startParsing();
            user.setRoot(root);
        } catch (FileInvalidException e) {
            e.printStackTrace();
        }
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        user.getRoot().toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, true, true);
        System.out.println(map.get("root").toString());
    }

    @AfterEach
    public void tearDown() {
        Root.resetInstance();
    }
}
