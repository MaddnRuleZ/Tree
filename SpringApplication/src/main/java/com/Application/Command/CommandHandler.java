package com.Application.Command;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.Factories.CommandFactory;
import com.Application.Command.Factories.PrintCommandFactory;
import com.Application.Exceptions.NumParamsException;
import com.Application.Exceptions.UnrecognizedCommandException;
import com.Application.TreeX;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * responsible for handling incoming commands and executing them based on their type
 */
public class CommandHandler {

    /**
     * contains a collection of command factories that are used to create specific types of commands
     */
    private final Map<String, CommandFactory> commandFactories;

    public CommandHandler(){
        TreeX treeX = new TreeX();
        this.commandFactories = initializeCommandFactories(treeX);
    }

    /**
     * recognizes the command type specified in the Json file, extracts
     * the given attributes, and creates the corresponding command
     * using the appropriate command factory, then executes the command
     * @param jsonFile contains command that has to be processed
     * @return jsonFile of tree structure, if it was possible to execute command
     */

    public JsonNode processCommand(JsonNode jsonFile) throws UnrecognizedCommandException, NumParamsException {

        if (jsonFile.isEmpty()) {
            throw new NumParamsException("No content");
        }

        Iterator<String> iterator = jsonFile.fieldNames();
        String commandType = iterator.next();
        if (iterator.hasNext()) {
            throw new NumParamsException("Too many arguments");
        }
        CommandFactory factory = this.commandFactories.get(commandType);

        if (factory != null) {
            JsonNode attributes = jsonFile.findValue(commandType);
            Command command = factory.createCommand(attributes);
            //command.execute();
        } else {
            throw new UnrecognizedCommandException(commandType);
        }

        //TODO Response

        return null;
    }


    /**
     * enlists the possible CommandFactories to the collection
     * @param treeX reference to the tree structure, printer and parser
     * @return map of factories
     */

    private Map<String, CommandFactory> initializeCommandFactories(TreeX treeX) {
        Map<String, CommandFactory> commandFactories = new HashMap<>();

        commandFactories.put("PrintCommand", new PrintCommandFactory(treeX));
        /*
        commandFactories.put("AddElement", new AddCommandFactory(treeX));
        commandFactories.put("DeleteElement", new AddCommandFactory(treeX));
        commandFactories.put("EditSummary", new EditSummaryCommandFactory(treeX));
        commandFactories.put("EditContent", new EditContentCommandFactory(treeX));
        commandFactories.put("EditComment", new EditCommentFactory(treeX));
        commandFactories.put("LoadFromGit", new LoadFromGitCommandFactory(treeX));
        commandFactories.put("LoadFromFolder", new LoadFromFolderCommandFactory(treeX));
        commandFactories.put("TreeMove", new TreeMoveCommandFactory(treeX));
        commandFactories.put("EditorMove", new EditorMoveCommandFactory(treeX));
         */

        return commandFactories;

    }

}
