package SpringApplication;

import SpringApplication.TestStubs.TestTree;
import com.Application.Command.CommandTypes.DeleteElementCommand;
import com.Application.Interpreter.Parser;
import com.Application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonParserTest {

    TestTree tree;

    @BeforeEach
    public void setUp() {
        tree = new TestTree();
    }

    @Test
    public void toJsonEditorTest() {
        String json = tree.root.toJsonEditor().toString();
        String formattedJsonString = formatJsonString(json);
        System.out.println(formattedJsonString);
        assertNotNull(json, "JsonEditor should not be null");
    }

    @Test
    public void toJsonTreeTest() {
        String json = tree.root.toJsonTree().toString();
        String formattedJsonString = formatJsonString(json);
        System.out.println(formattedJsonString);
        assertNotNull(json, "JsonEditor should not be null");
    }

    @Test
    public void EnvironmentJsonTest() {
        Parser parser = new Parser("src/TestDocuments/PSE_TEST_1.txt");
        Root root = (Root) parser.startParsing();
        //String json = root.toJsonEditor().toString();
        //String formattedJsonString = formatJsonString(json);
        //System.out.println(formattedJsonString);
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
