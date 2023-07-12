package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.DeleteElementCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.sectioning.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteCommandFactory implements CommandFactory {
    private final Root root;

    public DeleteCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        DeleteElementCommand command = mapper.convertValue(attributes, DeleteElementCommand.class);

        if (command.getElement() == null){
            throw new NumParamsException("Missing Parameter in DeleteCommand");
        }

        command.setRoot(this.root);
        return command;
    }
}
