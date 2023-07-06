package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.AddCommand;
import com.Application.Command.CommandTypes.Command;
import com.Application.Exceptions.NumParamsException;
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
    public Command createCommand(JsonNode attributes) throws NumParamsException {

        ObjectMapper mapper = new ObjectMapper();

        AddCommand command = mapper.convertValue(attributes, AddCommand.class);

        if (command.getContent() == null || command.getParent() == null || command.getPreviousChild() == null ){
            throw new NumParamsException("Missing Parameter in AddCommand");
        }

        //TODO
        return command;
    }

}
