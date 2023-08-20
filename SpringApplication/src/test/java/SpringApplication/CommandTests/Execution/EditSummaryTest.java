package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import com.application.User;
import com.application.command.types.EditSummaryCommand;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.parent.Sectioning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the execution of an EditSummaryCommand
 */
public class EditSummaryTest {

    /**
     * TestTree on which the test is executed on
     */
    ComplexTestTree tree;
    /**
     * Command to test
     */
    EditSummaryCommand command;
    /**
     * User on which the test is executed on
     */
    User user;
    /**
     * old summary of the element
     */
    String oldSummary;
    /**
     * new summary of the element that is received
     */
    String newSummaryIncoming;
    /**
     * new summary of the element that is stored in the element
     * equals newSummaryIncoming without the %\start{summary] and %\end{summary}
     */
    ArrayList<String> newSummaryStored = new ArrayList<>();

    /**
     * Sets up the test environment before each test
     * @throws ParseException if the testTree could not be created
     */
    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        command = new EditSummaryCommand();
        user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }

    /**
     * Tests the execution of an EditSummaryCommand
     */
    @Test
    public void editSummaryTest() {
        Element elem = tree.childrenList.get(0);
        oldSummary = elem.getSummary().toString();
        newSummaryIncoming = "newSummary\nnewSummary2";
        newSummaryStored.add("newSummary");
        newSummaryStored.add("newSummary2");

        command.setElement(elem.getId());
        command.setSummary(newSummaryIncoming);
        command.execute();

        assertEquals(newSummaryStored, elem.getSummary().getSummary(), "Summary should be changed");
    }

    /**
     * tests the execution of a editSummary command
     * with a bad summary
     */
    @Test
    public void emptySummaryTest() {
        Element elem = tree.childrenList.get(0);
        oldSummary = elem.getSummary().toString();
        newSummaryIncoming = "";

        assertTrue(elem.isChooseManualSummary());
        command.setElement(elem.getId());
        command.setSummary(newSummaryIncoming);
        command.execute();

        assertFalse(elem.isChooseManualSummary());
    }

    /**
     * tests if an exception is thrown if the element is not found
     */
    @Test
    public void ElementNotFoundTest() {
        UUID id = tree.notUsedUUID;
        newSummaryIncoming = "newSummary";
        newSummaryStored.add("newSummary");
        command.setElement(id);
        command.setSummary(newSummaryIncoming);
        command.execute();
        assertFalse(command.isSuccess(), "Command should fail");
    }

    /**
     * resets the test environment after each test
     */
    @AfterEach
    public void tearDown() {
        tree = null;
        command = null;
        user = null;
        oldSummary = null;
        newSummaryIncoming = null;
        newSummaryStored = null;
    }


}
