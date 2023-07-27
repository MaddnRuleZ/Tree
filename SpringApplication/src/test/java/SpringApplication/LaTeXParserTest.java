package SpringApplication;

import SpringApplication.TestStubs.TestTree;
import com.application.Exceptions.UnknownElementException;
import com.application.Tree.interfaces.LaTeXTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class LaTeXParserTest {

    TestTree tree;

    @BeforeEach
    public void setUp() {
        tree = new TestTree();
    }

    @Test
    public void parseTest() throws UnknownElementException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        tree.root.toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL);
        System.out.println(map.get("root").toString());
    }


}
