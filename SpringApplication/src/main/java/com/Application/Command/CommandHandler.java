package main.java.com.Application.Command;

import com.fasterxml.jackson.databind.JsonNode;
import main.java.com.Application.TreeX;
import main.java.com.Application.Command.Factories.*;
import main.java.com.Application.Command.CommandTypes.Command;

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
    private Map<String, CommandFactory> commandFactories;

    public CommandHandler(TreeX treeX){
        this.commandFactories = initializeCommandFactories(treeX);
    }

    /**
     * recognizes the command type specified in the Json file, extracts
     * the given attributes, and creates the corresponding command
     * using the appropriate command factory, then executes the command
     * @param jsonFile
     * @return
     */

    public JsonNode processCommand(JsonNode jsonFile){

        if (jsonFile.isEmpty()) {
            //TODO
        }

        Iterator<String> iterator = jsonFile.fieldNames();
        String commandType = iterator.next();
        CommandFactory factory = this.commandFactories.get(commandType);

        if (factory != null) {
            JsonNode attributes = jsonFile.findValue(commandType);
            Command command = factory.createCommand(attributes);
            command.execute();
        } else {
            //TODO Fehlerbehandlung CommandType nicht erkannt
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
        commandFactories.put("AddCommand", new AddCommandFactory(treeX));
        commandFactories.put("DeleteCommand", new AddCommandFactory(treeX));
        commandFactories.put("EditSummaryCommand", new EditSummaryCommandFactory(treeX));
        commandFactories.put("EditContentCommand", new EditContentCommandFactory(treeX));
        commandFactories.put("EditCommentCommand", new EditCommentFactory(treeX));
        commandFactories.put("LoadFromGitCommand", new LoadFromGitCommandFactory(treeX));
        commandFactories.put("LoadFromFolderCommand", new LoadFromFolderCommandFactory(treeX));
        commandFactories.put("TreeMoveCommand", new TreeMoveCommandFactory(treeX));
        commandFactories.put("EditorMoveCommand", new EditorMoveCommandFactory(treeX));

         */

        return commandFactories;

    }



}
