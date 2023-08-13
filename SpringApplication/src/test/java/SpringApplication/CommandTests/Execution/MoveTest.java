package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.Command;
import com.application.command.types.MoveElementCommand;
import com.application.exceptions.LevelException;
import com.application.exceptions.OwnChildException;
import com.application.exceptions.ParseException;
import com.application.tree.elements.childs.Child;
import com.application.tree.elements.parent.Environment;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.parent.Sectioning;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    TestTree tree;
    MoveElementCommand command;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = TestTree.createTestTree();
        command = new MoveElementCommand();
        User user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void moveElementWithPreviousElementTest(boolean isEditor) {
        Parent sec3 = tree.sectioningList.get(2);
        Child child4 = tree.childrenList.get(3);
        Child child5 = tree.childrenList.get(4);

        Parent oldParent = child4.getParentElement();
        command.setElement(child4.getId());
        command.setNewParent(sec3.getId());
        command.setPreviousElement(child5.getId());
        command.setEditor(isEditor);

        command.execute();

        assertEquals(sec3.getChildren().get(0), child5, "Child 5 should be first child of Sectioning 3");
        assertEquals(sec3.getChildren().get(1), child4, "Child 4 should be second child of Sectioning 3");
        assertFalse(oldParent.getChildren().contains(child4), "Old parent should not contain child 4");
    }
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void moveElementWithoutPreviousElementTest(boolean isEditor) {
        Parent sec3 = tree.sectioningList.get(2);
        Child child4 = tree.childrenList.get(3);
        Child child5 = tree.childrenList.get(4);

        Parent oldParent = child4.getParentElement();
        command.setElement(child4.getId());
        command.setNewParent(sec3.getId());
        command.setPreviousElement(null);
        command.setEditor(isEditor);

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
            command.moveElement(sec2, env1, null, 1);
        });
        assertFalse(env1.getChildren().contains(sec2), "Environment 1 should not contain Sectioning 2");
        assertEquals(tree.sectioningList.get(0), sec2.getParentElement(), "Sectioning 1 should be parent of Sectioning 2");
    }

    @Test
    public void moveTooDeep() {
        Sectioning sec2 = tree.sectioningList.get(1);
        Sectioning sec11 = tree.sectioningList.get(10);

        assertThrows(LevelException.class, () -> {
            command.moveElement(sec2, sec11, null, 1);
        });
        assertFalse(sec11.getChildren().contains(sec2), "Sectioning 11 should not contain Sectioning 2");
        assertEquals(tree.sectioningList.get(0), sec2.getParentElement(), "Sectioning 1 should be parent of Sectioning 2");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void ElementNotFoundTest(boolean isEditor) {
        UUID id = tree.notUsedUUID;
        Parent sec2 = tree.sectioningList.get(1);

        command.setElement(id);
        command.setNewParent(sec2.getId());
        command.setPreviousElement(null);
        command.setEditor(isEditor);
        command.execute();

        assertFalse(command.isSuccess(), "Command should not be successful");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void newParentTypeChild(boolean isEditor) {
        Parent sec2 = tree.sectioningList.get(1);
        Child child4 = tree.childrenList.get(3);
        Child child5 = tree.childrenList.get(4);

        Parent oldParent = child4.getParentElement();
        command.setElement(child4.getId());
        command.setNewParent(child5.getId());
        command.setPreviousElement(null);
        command.setEditor(isEditor);
        command.execute();

        assertFalse(command.isSuccess(), "Command should not be successful");
        assertEquals(oldParent, child4.getParentElement(), "Child 4 should not have a new parent");
    }


    @AfterEach
    public void tearDown() {
        tree = null;
        command = null;
    }
}
