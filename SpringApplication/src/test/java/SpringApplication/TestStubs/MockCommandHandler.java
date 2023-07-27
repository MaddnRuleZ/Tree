package SpringApplication.TestStubs;

import com.application.command.CommandHandler;
import com.application.command.types.Command;
import com.application.command.factories.CommandFactory;
import com.application.exceptions.NumParamsException;
import com.application.exceptions.ProcessingException;
import com.application.exceptions.UnrecognizedCommandException;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;


public class MockCommandHandler extends CommandHandler {

    private final Map<String, CommandFactory> commandFactories;
    private boolean success;


    public MockCommandHandler(User user) {
        super(user);
        CommandHandler commandHandler = new CommandHandler(user);
        this.commandFactories = commandHandler.getCommandFactories();
        this.success = false;
    }


    public JsonNode processCommand(JsonNode jsonFile) throws ProcessingException {
        JsonNode response;
        if (jsonFile.isEmpty()) {
            throw new NumParamsException("jsonFile");
        }

        Iterator<String> iterator = jsonFile.fieldNames();
        String commandType = iterator.next();
        CommandFactory factory = this.commandFactories.get(commandType);

        if (factory != null) {
            JsonNode attributes = jsonFile.findValue(commandType);
            Command command = factory.createCommand(attributes);
            //----------------------------------------
            // Change in comparison to CommandHandler
            response = command.generateResponse(command.isSuccess());
            //----------------------------------------
            this.success = command.isSuccess();
            return response;
        } else {
            throw new UnrecognizedCommandException(commandType);
        }
    }
}
