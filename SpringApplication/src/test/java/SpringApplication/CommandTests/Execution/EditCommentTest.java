package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import com.application.User;
import com.application.command.types.EditCommentCommand;
import com.application.command.types.EditContentCommand;
import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.additionalInfo.Comment;
import com.application.tree.elements.parent.Sectioning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the execution of an EditCommentCommand
 */
public class EditCommentTest {

    /**
     * TestTree on which the test is executed on
     */
    ComplexTestTree tree;
    /**
     * Command to test
     */
    EditCommentCommand command;
    /**
     * User on which the test is executed on
     */
    User user;
    /**
     * old comment of the element
     */
    String oldComment;
    /**
     * new comment of the element that is received
     */
    String newCommentIncoming;
    /**
     * new comment of the element that is stored in the element
     * equals newCommentIncoming without the %
     */
    final ArrayList<String> newCommentStored = new ArrayList<>();

    /**
     * Sets up the test environment before each test
     * @throws ParseException
     */
    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        command = new EditCommentCommand();
        user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }

    /**
     * tests the execution of a editComment command
     * with a suitable comment
     */
    @Test
    public void editCommentTest() {
        Sectioning sec = tree.sectioningList.get(3);
        oldComment = sec.getComment().toString();
        newCommentIncoming = "%newComment";
        newCommentStored.add("newComment");

        command.setElement(sec.getId());
        command.setComment(newCommentIncoming);
        command.execute();

        assertEquals(newCommentStored, sec.getComment().getComments(), "Comment should be changed");
    }

    /**
     * tests if an exception is thrown if the element is not found
     */
    @Test
    public void ElementNotFoundTest() {
        UUID id = tree.notUsedUUID;
        newCommentIncoming = "%newComment";
        newCommentStored.add("newComment");
        command.setElement(id);
        command.setComment(newCommentIncoming);
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
        oldComment = null;
        newCommentIncoming = null;
        newCommentStored.clear();
    }

}
