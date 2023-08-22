package SpringApplication.ParserTests;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.interpreter.Parser;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LaTeXParserTest {

    @Test
    public void parseTest() throws UnknownElementException {
        ComplexTestTree tree = new ComplexTestTree();
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        tree.root.toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, true, true);
        System.out.println(map.get("root").toString());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/TestDocuments/BeginOnSection.tex"
    })
    public void testLaTeXFiles(String path) throws UnknownElementException, IOException {
        Parser parser = new Parser(path);
        User user = new User();

        try {
            Root root = (Root) parser.startParsing();
            user.setRoot(root);
        } catch (FileInvalidException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        user.getRoot().toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, true, true);

        List<String> originalLines = readLinesFromFile(path);
        List<String> modifiedLines = map.get("root").toString().lines().toList();

        List<String> normalizedOriginal = normalizeContent(originalLines);
        List<String> normalizedModified = normalizeContent(modifiedLines);

        assertEquals(normalizedOriginal, normalizedModified);
    }

    @Test
    public void UnknownElementTest() throws ParseException {
        TestTree tree = TestTree.createTestTree();
        User user = new User();
        user.setRoot(tree.root);

        Sectioning sec = tree.sectioningList.get(tree.sectioningList.size() - 1);
        Sectioning newSec = new Sectioning("\\section", 8);
        newSec.setParent(sec);
        sec.getChildren().add(newSec);

        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());

        assertThrows(UnknownElementException.class, () ->
                user.getRoot().toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, true, true),
                "Should throw UnknownElementException");
    }

    @AfterEach
    public void tearDown() {
        Root.resetInstance();
    }

    private static List<String> readLinesFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static List<String> normalizeContent(List<String> lines) {
        List<String> normalized = new ArrayList<>();
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty()) {
                normalized.add(trimmedLine);
            }
        }
        return normalized;
    }
}
