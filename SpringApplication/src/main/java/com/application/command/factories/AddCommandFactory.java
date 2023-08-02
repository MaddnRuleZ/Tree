package com.application.command.factories;

import com.application.command.types.AddCommand;
import com.application.command.types.Command;
import com.application.exceptions.NumParamsException;
import com.application.tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an AddCommand
 */
public class AddCommandFactory implements CommandFactory {
    private User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public AddCommandFactory(User user) {
        this.user = user;
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        AddCommand command;
        try {
           command = mapper.convertValue(attributes, AddCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("AddElement");
        }

        if (command.getContent() == null || command.getParent() == null){
            throw new NumParamsException("AddElement");
        }

        command.setUser(this.user);
        return command;
    }

}
