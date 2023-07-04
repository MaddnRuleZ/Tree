package SpringApplication.Command;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import SpringApplication.TreeX;

public class CommandHandler {
    private Map<String, CommandFactory> commandFactories;

    public CommandHandler(TreeX treeX){
        //TODO
    }

    public JsonNode processCommand(String jsonFile){
        //TODO
    }
    
    public Map<String, CommandFactory> initializeCommandFactories(TreeX treeX) {
        //TODO
    }

}
