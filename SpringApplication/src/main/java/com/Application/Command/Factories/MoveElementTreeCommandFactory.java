package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.MoveElementTreeCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an MoveElementTreeCommand
 */
public class MoveElementTreeCommandFactory implements CommandFactory {
    private final Root root;

    public MoveElementTreeCommandFactory(User user) {
        this.root = user.getRoot();
    }
    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        MoveElementTreeCommand command;
        try {
            command = mapper.convertValue(attributes, MoveElementTreeCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("MoveElementTree");
        }

        if (command.getElement() == null || command.getNewParent() == null || command.getPreviousElement() == null ){
            throw new NumParamsException("MoveElementTree");
        }

        command.setRoot(this.root);
        return command;
    }
}
