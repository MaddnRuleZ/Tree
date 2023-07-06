package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.AddCommand;
import com.Application.Command.CommandTypes.Command;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Application.TreeX;

public class AddCommandFactory implements CommandFactory {
    //private Root root;

    public AddCommandFactory(TreeX treeX) {
        //this.root = treeX.getRoot();
    }
    @Override
    @JsonIgnoreProperties(value = {"root"})
    public Command createCommand(JsonNode attributes) {

        ObjectMapper mapper = new ObjectMapper();
        Command command = mapper.convertValue(attributes, AddCommand.class);

        //TODO
        return null;
    }

}
