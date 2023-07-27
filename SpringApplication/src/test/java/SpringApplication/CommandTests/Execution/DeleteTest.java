package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.TestTree;
import com.application.Command.CommandTypes.DeleteElementCommand;
import com.application.Tree.elements.parent.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


// Tree structure:
//                        root
//                          |
//      --------------Sectioning1---------------
//               /                    |    |    \
//    -------Sec2-----------        Sec3   Sec4   Child1
//   /  |     |       |    \          |      |
// Sec5 Env1 Child2 Child3 Child4   Child5  Env2
//        |
//       Sec6

public class DeleteTest {

    TestTree tree;
    DeleteElementCommand command;

    @BeforeEach
    public void setUp() {
        tree = new TestTree();
        command = new DeleteElementCommand();
    }


    @Test
    public void deleteSectioningCascading() {
        Parent sec2 = tree.sectioningList.get(1);
        command.setCascading(true);
        command.delete(sec2);

        assertFalse(tree.sectioningList.get(0).getChildElements().contains(sec2), "Sectioning 2 should be deleted");

    }

    @Test
    public void deleteSectioningNotCascading() {
        Parent sec1 = tree.sectioningList.get(0);
        Parent sec2 = tree.sectioningList.get(1);
        command.setCascading(false);
        command.delete(sec2);

        assertEquals(tree.sectioningList.get(4), sec1.getChildElements().get(0), "Sectioning 5 should be first child of Sectioning 1");
        assertEquals(tree.environmentList.get(0), sec1.getChildElements().get(1), "Environment 1 should be second child of Sectioning 1");
        assertEquals(tree.childrenList.get(1), sec1.getChildElements().get(2), "Child 2 should be third child of Sectioning 1");
        assertEquals(tree.childrenList.get(2), sec1.getChildElements().get(3), "Child 3 should be child of Sectioning 1");
        assertEquals(tree.childrenList.get(3), sec1.getChildElements().get(4), "Child 4 should be child of Sectioning 1");

    }

}


