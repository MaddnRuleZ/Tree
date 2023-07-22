package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.TestTree;
import com.Application.Tree.elements.childs.Child;
import com.Application.Tree.elements.parent.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHelperMethodsTest {
    // Tree structure:
    //                        root
    //                          |
    //      --------------Sectioning1---------------
    //               /                    |    |    \
    //    -------Sec2-----------        Sec3   Sec4   Child1
    //   /   |     |       |    \         |      |
    // Sec5 Env1 Child2 Child3 Child4   Child5  Env2
    //        |
    //       Sec6
    TestTree tree;

    @BeforeEach
    public void setUp() {
        tree = new TestTree();
    }

    /**
     * tests the method searchForId
     */
    @Test
    public void searchForIdTest() {
        Parent sec1 = tree.sectioningList.get(0);
        Child child3 = tree.childrenList.get(2);
        UUID child3Id = child3.getId();

        assertEquals(child3, tree.root.searchForID(child3Id), "Child 3 should be found");
    }


    /**
     * tests the method calculateLevelFromElement
     */
    @Test
    public void calculateLevelFromElementTest() {
        Parent sec1 = tree.sectioningList.get(0);
        Child child3 = tree.childrenList.get(2);
        assertEquals(2, child3.calculateLevelFromElement(), "Child 3 should be on level 2");
    }

    /**
     * tests the method levelOfDeepestSectioningChild
     */
    @Test
    public void calculateLevelFromDeepestSectioningChildTest() {
        Parent sec1 = tree.sectioningList.get(0);
        Parent sec2 = tree.sectioningList.get(1);
        Parent sec3 = tree.sectioningList.get(2);
        assertEquals(3, sec1.levelOfDeepestSectioningChild(), "Deepest Child of sec1 should be on level 3");
        assertEquals(2, sec2.levelOfDeepestSectioningChild(), "Deepest Child of sec2 should be on level 2");
        assertEquals(1, sec3.levelOfDeepestSectioningChild(), "Deepest Child of sec3 should be on level 1");
    }
}
