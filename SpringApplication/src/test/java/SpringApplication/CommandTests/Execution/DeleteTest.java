package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.DeleteElementCommand;
import com.application.exceptions.ParseException;
import com.application.tree.elements.parent.Parent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests the execution of an DeleteCommand
 */
public class DeleteTest {

    /**
     * TestTree on which the test is executed on
     */
    TestTree tree;
    /**
     * Command to test
     */
    DeleteElementCommand command;
    /**
     * User on which the test is executed on
     */
    User user;

    /**
     * Sets up the test environment before each test
     * @throws ParseException if the testTree could not be created
     */
    @BeforeEach
    public void setUp() throws ParseException {
        tree = TestTree.createTestTree();
        command = new DeleteElementCommand();
        user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }


    /**
     * Tests if the content is deleted correctly if cascading is true
     */
    @Test
    public void deleteSectioningCascading() {
        Parent sec2 = tree.sectioningList.get(1);
        command.setCascading(true);
        command.setElement(sec2.getId());
        command.execute();

        assertFalse(tree.sectioningList.get(0).getChildren().contains(sec2), "Sectioning 2 should be deleted");

    }

    /**
     * Tests if the content is deleted correctly if cascading is false
     */
    @Test
    public void deleteSectioningNotCascading() {
        Parent sec1 = tree.sectioningList.get(0);
        Parent sec2 = tree.sectioningList.get(1);
        command.setCascading(false);
        command.setElement(sec2.getId());
        command.execute();

        assertEquals(tree.sectioningList.get(4), sec1.getChildren().get(0), "Sectioning 5 should be first child of Sectioning 1");
        assertEquals(tree.environmentList.get(0), sec1.getChildren().get(1), "Environment 1 should be second child of Sectioning 1");
        assertEquals(tree.childrenList.get(1), sec1.getChildren().get(2), "Child 2 should be third child of Sectioning 1");
        assertEquals(tree.childrenList.get(2), sec1.getChildren().get(3), "Child 3 should be child of Sectioning 1");
        assertEquals(tree.childrenList.get(3), sec1.getChildren().get(4), "Child 4 should be child of Sectioning 1");
    }

    /**
     * Tests if the command executes correctly if an element is deleted from the first level
     * @param isCascading if the command is cascading
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void deleteElementFromFirstLevel(boolean isCascading) {
        Parent sec1 = tree.sectioningList.get(0);
        command.setCascading(isCascading);
        command.setElement(sec1.getId());
        command.execute();

        assertFalse(tree.root.getChildren().contains(sec1), "Sectioning 1 should be deleted");

    }

    /**
     * Tests if the exception is thrown if the element is not found
     */
    @Test
    public void ElementNotFound() {
        command.setCascading(true);
        command.setElement(tree.notUsedUUID);
        command.execute();

        assertFalse(command.isSuccess(), "Command should not execute successfully");
    }

    /**
     * resets the test environment after each test
     */
    @AfterEach
    public void tearDown() {
        tree = null;
        command = null;
        user = null;
    }

}


