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

public class EditSummaryTest {

    ComplexTestTree tree;
    EditSummaryCommand command;
    User user;
    String oldSummary;
    String newSummaryIncoming;
    ArrayList<String> newSummaryStored = new ArrayList<>();

    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        command = new EditSummaryCommand();
        user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }

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

    @Test
    public void ElementNotFoundTest() {
        UUID id = tree.notUsedUUID;
        newSummaryIncoming = "newSummary";
        newSummaryStored.add("newSummary");
        command.setElement(id);
        command.setSummary(newSummaryIncoming);

        assertFalse(command.isSuccess(), "Command should fail");
    }

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
