package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.TestTree;
import com.application.exceptions.ParseException;
import com.application.tree.elements.childs.Child;
import com.application.tree.elements.parent.Parent;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CommandHelperMethodsTest {
    TestTree tree;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = TestTree.createTestTree();
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
        assertEquals(7, sec1.levelOfDeepestSectioningChild(), "Deepest Child of sec1 should be on level 7");
        assertEquals(2, sec2.levelOfDeepestSectioningChild(), "Deepest Child of sec2 should be on level 2");
        assertEquals(1, sec3.levelOfDeepestSectioningChild(), "Deepest Child of sec3 should be on level 1");
    }

    @Test
    public void checkOwnChild() {
        Parent sec2 = tree.sectioningList.get(1);
        Parent sec3 = tree.sectioningList.get(2);
        assertFalse(sec2.checkOwnChild(sec3), "sec2 should not be child of sec3");
    }

    @AfterEach
    public void tearDown() {
        tree = null;
    }
}
