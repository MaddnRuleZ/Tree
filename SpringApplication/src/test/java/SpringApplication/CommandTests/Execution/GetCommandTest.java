package SpringApplication.CommandTests.Execution;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.User;
import com.application.command.types.ExportCommand;
import com.application.command.types.GetCommand;
import com.application.exceptions.ParseException;
import com.application.printer.FilePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

public class GetCommandTest {

    TestTree tree;
    GetCommand command;
    User user;
    FilePrinter printer;

    @BeforeEach
    public void setUp() throws ParseException {
        tree = new ComplexTestTree();
        user = new User();
        user.setRoot(tree.root);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void getTest(boolean isEditor) {
        command = new GetCommand(user, isEditor);
        command.execute();

        //TODO
    }
}
