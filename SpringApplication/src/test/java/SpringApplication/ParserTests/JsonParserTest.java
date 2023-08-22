package SpringApplication.ParserTests;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.ProcessingException;
import com.application.interpreter.Parser;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Ignore("This test is better done in GetCommandTest")
public class JsonParserTest {

    TestTree tree;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
    }

    @Test
    public void toJsonEditorTest() throws ProcessingException {
        String json = tree.root.toJsonEditor().toString();
        String formattedJsonString = formatJsonString(json);
        System.out.println(formattedJsonString);
        assertNotNull(json, "JsonEditor should not be null");
    }

    @Test
    public void toJsonTreeTest() throws ProcessingException {
        String json = tree.root.toJsonTree().toString();
        String formattedJsonString = formatJsonString(json);
        System.out.println(formattedJsonString);
        assertNotNull(json, "JsonEditor should not be null");
    }

    private static String formatJsonString(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
