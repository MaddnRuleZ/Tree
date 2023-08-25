package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import com.application.User;
import com.application.command.types.AddCommand;
import com.application.tree.Element;
import com.application.tree.elements.parent.Environment;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.interfaces.LaTeXTranslator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests the execution of an AddCommand
 */
public class AddElementTest {
    /**
     * Command to test
     */
    AddCommand command;
    /**
     * User on which the test is executed on
     */
    User user;
    /**
     * TestTree on which the test is executed on
     */
    ComplexTestTree tree;

    /**
     * Sets up the test environment before each test
     */
    @BeforeEach
    public void setUp() {
        command = new AddCommand();
        user = new User();
        tree = new ComplexTestTree();
        user.setRoot(tree.root);
        command.setUser(user);
    }


    /**
     * Tests the execution of an AddCommand
     * Tests if the content is added correctly
     * @param testCase TestCase to test
     */
    @ParameterizedTest
    @MethodSource("elementTestCases")
    public void addElementTest(ElementTestCase testCase) {
        String input = testCase.getInput();
        String expectedContent = testCase.getExpectedContent();
        String expectedOptions = testCase.getExpectedOptions();
        String expectedHeader = testCase.getExpectedHeader();
        List<String> expectedComment = testCase.getExpectedComment();

        Sectioning sec = tree.sectioningList.get(0);
        command.setParent(sec.getId());
        command.setContent(input);
        command.setPreviousChild(sec.getChildren().get(0).getId());
        command.execute();

        Element newElem = sec.getChildren().get(0);

        assertEquals(expectedContent, newElem.getContent(), "Content should be added");
        assertEquals(expectedOptions, newElem.getOptions(), "Options should be added");
        assertEquals(expectedComment, newElem.getComment().getComments(), "Comment should be added");
        if(newElem instanceof Environment) {
            assertEquals(expectedHeader, ((Environment) newElem).getHeader(), "Header should be added");
        }

        print(expectedContent);
    }

    /**
     * Tests the correct failure Recognition if the parent is not a parentType
     */
    @Test
    public void newParentTypeChild() {
        command.setParent(tree.childrenList.get(0).getId());
        command.setContent("some content");
        command.setPreviousChild(null);
        command.execute();

        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }

    /**
     * Tests the correct failure Recognition if the parent is not found
     */
    @ParameterizedTest
    @CsvSource({"0, 1", "1, 0"})
    public void ElementNotFound(int parent, int previousChild) {
        UUID[] uuids = {tree.sectioningList.get(0).getId(), tree.notUsedUUID};

        command.setParent(uuids[parent]);
        command.setContent("some content");
        command.setPreviousChild(uuids[previousChild]);
        command.execute();

        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }

    /**
     * Tests the correct failure Recognition if there is no content to be added
     */
    @Test
    public void ParseException() {
        command.setParent(tree.childrenList.get(0).getId());
        command.setContent("%no content to create an Element");
        command.setPreviousChild(null);
        command.execute();

        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }


    /**
     * resets the test environment after each test
     */
    @AfterEach
    public void tearDown() {
        command = null;
        user = null;
        tree = null;
    }

    public void print(String expectedContent) {
        File file = new File("src/test/resources/PrinterTestOutput/AddTestOutput/AddTest" + expectedContent + ".txt");
        try {
            Map<String, StringBuilder> map = new HashMap<>();
            map.put("root", new StringBuilder());
            user.getRoot().toLaTeX(map, "root", LaTeXTranslator.INIT_INDENTATION_LEVEL, true, true);
            Files.writeString(file.toPath(), map.get("root").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TestCases for the addElementTest
     * @return Stream of TestCases each one contains the input, expectedContent, expectedOptions and expectedComment
     * for an structureElement recognised by the parser
     */
    static Stream<Arguments> elementTestCases() {
        return Stream.of(
                new ElementTestCase(
                        "\\section*{newly added section}",
                        "newly added section",
                        "*",
                        null,
                        List.of()
                ),
                new ElementTestCase(
                        "\\begin{enumerate} \n \\item item1 \n\\item item2 \n \\end{enumerate}",
                        " \\item item1 \n\\item item2 \n",
                        null,
                        "enumerate",
                        List.of()
                ),
                new ElementTestCase(
                        "\\begin{equation}[someOption] \n \\frac{1}{2} \n \\end{equation}",
                        " \\frac{1}{2} \n",
                        "someOption",
                        "equation",
                        List.of()
                ),
                new ElementTestCase(
                        "\\begin{figure}[ht] \n \\includegraphics[width=\\linewidth]{test.png} \n \\caption{caption1}\\end{figure}",
                        "figure",
                        "ht",
                        "figure",
                        List.of()
                ),
                new ElementTestCase(
                        "\\begin{algorithmic} \\State $i \\gets 0$ \n \\end{algorithmic}",
                        "\\State $i \\gets 0$ \n",
                        null,
                        "algorithmic",
                        List.of()
                ),
                new ElementTestCase(
                        "\\label{new label1}",
                        "new label1",
                        null,
                        null,
                        List.of()
                ),
                new ElementTestCase(
                        "just newly added text",
                        "just newly added text\n",
                        null,
                        null,
                        List.of()
                ),
                new ElementTestCase(
                        "%comment of new element\n new element just text",
                        "new element just text\n",
                        null,
                        null,
                        new ArrayList<String>() {{
                            add("comment of new element");
                        }}
                )
        ).map(Arguments::of);
    }



    public static class ElementTestCase {
        private final String input;
        private final String expectedContent;
        private final String expectedOptions;
        private final List<String> expectedComment;

        private final String expectedHeader;

        public ElementTestCase(String input, String expectedContent, String expectedOptions, String expectedHeader, List<String> expectedComment) {
            this.input = input;
            this.expectedContent = expectedContent;
            this.expectedOptions = expectedOptions;
            this.expectedHeader = expectedHeader;
            this.expectedComment = expectedComment;
        }

        public String getInput() {
            return input;
        }

        public String getExpectedContent() {
            return expectedContent;
        }

        public String getExpectedOptions() {
            return expectedOptions;
        }

        public List<String> getExpectedComment() {
            return expectedComment;
        }

        public String getExpectedHeader() {
            return expectedHeader;
        }

    }
}
