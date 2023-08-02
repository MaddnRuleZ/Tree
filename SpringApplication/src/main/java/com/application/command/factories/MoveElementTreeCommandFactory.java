package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.MoveElementTreeCommand;
import com.application.exceptions.NumParamsException;
import com.application.tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an MoveElementTreeCommand
 */
public class MoveElementTreeCommandFactory implements CommandFactory {
    private User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public MoveElementTreeCommandFactory(User user) {
        this.user = user;
    }
    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        MoveElementTreeCommand command;
        try {
            command = mapper.convertValue(attributes, MoveElementTreeCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("MoveElementTree - mapping failed");
        }

        if (command.getElement() == null || command.getNewParent() == null || command.getPreviousElement() == null ){
            throw new NumParamsException("MoveElementTree - parameter not set");
        }

        command.setUser(this.user);
        return command;
    }
}
