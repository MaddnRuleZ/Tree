package com.application.Command.Factories;

import com.application.Command.CommandTypes.Command;
import com.application.Command.CommandTypes.MoveElementTreeCommand;
import com.application.Exceptions.NumParamsException;
import com.application.Tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an MoveElementTreeCommand
 */
public class MoveElementTreeCommandFactory implements CommandFactory {
    /**
     * root of the LaTeX-Project
     */
    private final Root root;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
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
            throw new NumParamsException("MoveElementTree - mapping failed");
        }

        if (command.getElement() == null || command.getNewParent() == null || command.getPreviousElement() == null ){
            throw new NumParamsException("MoveElementTree - parameter not set");
        }

        command.setRoot(this.root);
        return command;
    }
}
