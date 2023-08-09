package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import com.application.User;
import com.application.command.types.EditCommentCommand;
import com.application.command.types.EditContentCommand;
import com.application.exceptions.ParseException;
import com.application.tree.additionalInfo.Comment;
import com.application.tree.elements.parent.Sectioning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EditCommentTest {

    ComplexTestTree tree;
    EditCommentCommand command;
    User user;

    String oldComment;
    String newCommentIncoming;

    ArrayList<String> newCommentStored = new ArrayList<>();

    @BeforeEach
    public void setUp() throws ParseException {
        tree = ComplexTestTree.createTestTree();
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
