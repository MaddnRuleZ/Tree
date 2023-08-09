package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.MoveElementEditorCommand;
import com.application.exceptions.OwnChildException;
import com.application.exceptions.ParseException;
import com.application.tree.elements.childs.Child;
import com.application.tree.elements.parent.Environment;
import com.application.tree.elements.parent.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tree structure:
//                        root
//                          |
//      --------------Sectioning1---------------
//               /                    |    |    \
//    -------Sec2-----------        Sec3   Sec4   Child1
//   /  |     |       |    \          |      |
// Sec5 Env1 Child2 Child3 Child4   Child5  Env2
//        |
//       Sec6
public class MoveTest {


    TestTree tree;
    MoveElementEditorCommand command;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = TestTree.createTestTree();
        command = new MoveElementEditorCommand();
        User user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }

    @Test
    public void moveElementWithPreviousElementTest() {
        Parent sec3 = tree.sectioningList.get(2);
        Child child4 = tree.childrenList.get(3);
        Child child5 = tree.childrenList.get(4);

        Parent oldParent = child4.getParentElement();
        command.setElement(child4.getId());
        command.setNewParent(sec3.getId());
        command.setPreviousElement(child5.getId());

        command.execute();

        assertEquals(sec3.getChildren().get(0), child5, "Child 5 should be first child of Sectioning 3");
        assertEquals(sec3.getChildren().get(1), child4, "Child 4 should be second child of Sectioning 3");
        assertFalse(oldParent.getChildren().contains(child4), "Old parent should not contain child 4");
    }
    @Test
    public void moveElementWithoutPreviousElementTest() {
        Parent sec3 = tree.sectioningList.get(2);
        Child child4 = tree.childrenList.get(3);
        Child child5 = tree.childrenList.get(4);

        Parent oldParent = child4.getParentElement();
        command.setElement(child4.getId());
        command.setNewParent(sec3.getId());
        command.setPreviousElement(null);

        command.execute();

        assertEquals(sec3.getChildren().get(0), child4, "Child 4 should be first child of Sectioning 3");
        assertEquals(sec3.getChildren().get(1), child5, "Child 5 should be second child of Sectioning 3");
        assertFalse(oldParent.getChildren().contains(child4), "Old parent should not contain child 4");
    }

    @Test
    public void OwnChildException() {
        Parent sec2 = tree.sectioningList.get(1);
        Environment env1 = tree.environmentList.get(0);

        assertThrows(OwnChildException.class, () -> {
            command.moveElement(sec2, env1, null, 0);
        });
        assertFalse(env1.getChildren().contains(sec2), "Environment 1 should not contain Sectioning 2");
        assertEquals(tree.sectioningList.get(0), sec2.getParentElement(), "Sectioning 1 should be parent of Sectioning 2");
    }
}
