package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.MoveElementCommand;
import com.application.exceptions.NumParamsException;
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
        MoveElementCommand command;
        try {
            command = mapper.convertValue(attributes, MoveElementCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("MoveElementTree - mapping failed");
        }

        if (command.getElement() == null){
            throw new NumParamsException("MoveElementTree - parameter not set");
        }

        command.setUser(this.user);
        command.setEditor(false);
        return command;
    }
}
