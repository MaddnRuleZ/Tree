package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.DeleteElementCommand;
import com.application.exceptions.NumParamsException;
import com.application.tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create a DeleteCommand
 */
public class DeleteCommandFactory implements CommandFactory {

    private User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public DeleteCommandFactory(User user) {
        this.user = user;
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        DeleteElementCommand command;
        try {
            command = mapper.convertValue(attributes, DeleteElementCommand.class);
        } catch (IllegalArgumentException e){
            throw new NumParamsException("DeleteElement");
        }
        // throws only an Exception if the element is null, not if cascading missing in jsonFile
        // default value of cascading is false
        if (command.getElement() == null){
            throw new NumParamsException("DeleteElement");
        }

        command.setUser(this.user);
        return command;
    }
}
