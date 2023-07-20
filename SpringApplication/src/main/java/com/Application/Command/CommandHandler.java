package com.Application.Command;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.Factories.*;
import com.Application.Exceptions.NumParamsException;
import com.Application.Exceptions.ProcessingException;
import com.Application.Exceptions.UnrecognizedCommandException;
import com.Application.Tree.elements.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * responsible for handling incoming commands and executing them based on their type
 */
@Component
public class CommandHandler {

    /**
     * contains a collection of command factories that are used to create specific types of commands
     */
    private final Map<String, CommandFactory> commandFactories;
    private boolean success;

    public CommandHandler(User user){
        this.commandFactories = initializeCommandFactories(user);
    }

    /**
     * recognizes the command type specified in the Json file, extracts
     * the given attributes, and creates the corresponding command
     * using the appropriate command factory, then executes the command
     * @param jsonFile contains command that has to be processed
     * @return jsonString of tree structure, if it was possible to execute command
     */

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
            response = command.execute();
            this.success = command.isSuccess();
            return response;
        } else {
            throw new UnrecognizedCommandException(commandType);
        }
    }


    /**
     * enlists the possible CommandFactories to the collection
     * @param user reference to the tree structure, printer and parser
     * @return map of factories
     */

    private Map<String, CommandFactory> initializeCommandFactories(User user) {
        Map<String, CommandFactory> commandFactories = new HashMap<>();

        //commandFactories.put("PrintCommand", new PrintCommandFactory(user));
        commandFactories.put("AddElement", new AddCommandFactory(user));
        commandFactories.put("DeleteElement", new DeleteCommandFactory(user));
        commandFactories.put("EditSummary", new EditSummaryCommandFactory(user));
        commandFactories.put("EditContent", new EditContentCommandFactory(user));
        commandFactories.put("EditComment", new EditCommentCommandFactory(user));
        commandFactories.put("LoadFromGit", new LoadFromGitCommandFactory(user));
        commandFactories.put("LoadFromFolder", new LoadFromFolderCommandFactory(user));
        commandFactories.put("MoveElementTree", new MoveElementTreeCommandFactory(user));
        commandFactories.put("MoveElementEditor", new MoveElementEditorCommandFactory(user));


        return commandFactories;

    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, CommandFactory> getCommandFactories() {
        return commandFactories;
    }
}
