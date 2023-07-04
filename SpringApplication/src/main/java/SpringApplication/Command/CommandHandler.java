package SpringApplication.Command;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import SpringApplication.TreeX;

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

    public JsonNode processCommand(String jsonFile){
        List<String> attributes = extractCommand(json);
        String commandType = attributes.pop(0);
        CommandFactory factory = this.commandFactories.get(commandType);

        if (factory != null) {
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
     * @param treeX refernce to the tree structure, printer and parser
     * @return map of factories
     */

    private Map<String, CommandFactory> initializeCommandFactories(TreeX treeX) {
        Map<String, CommandFactory> commandFactories = new Map<>();

        commandFactories.add("PrintCommand", new PrintCommandFactory(treeX));
        commandFactories.add("AddCommand", new AddCommandFactory(treeX));
        commandFactories.add("DeleteCommand", new AddCommandFactory(treeX));
        commandFactories.add("EditSummaryCommand", new EditSummaryCommandFactory(treeX));
        commandFactories.add("EditContentCommand", new EditContentCommandFactory(treeX));
        commandFactories.add("EditCommentCommand", new EditCommentFactory(treeX));
        commandFactories.add("LoadFromGitCommand", new LoadFromGitCommandFactory(treeX));
        commandFactories.add("LoadFromFolderCommand", new LoadFromFolderCommandFactory(treeX));
        commandFactories.add("TreeMoveCommand", new TreeMoveCommandFactory(treeX));
        commandFactories.add("EditorMoveCommand", new EditorMoveCommandFactory(treeX));

        return commandFactories;

    }

    /**
     * generates a list of String out of an jsonString
     * @param jsonFile
     * @return list of extracted Strings
     */
    private List<String> extractCommand(String jsonFile) {
        //TODO
    }


}
