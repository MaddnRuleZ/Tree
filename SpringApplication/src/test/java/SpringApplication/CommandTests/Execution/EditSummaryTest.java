package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import com.application.User;
import com.application.command.types.EditContentCommand;
import com.application.exceptions.ParseException;
import org.junit.jupiter.api.BeforeEach;

public class EditSummaryTest {

    ComplexTestTree tree;
    EditContentCommand command;
    User user;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        command = new EditContentCommand();
        user = new User();
        user.setRoot(tree.root);
        command.setUser(user);
    }

}
