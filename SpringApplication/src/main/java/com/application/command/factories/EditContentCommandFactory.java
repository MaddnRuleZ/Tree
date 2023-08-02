package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.EditContentCommand;
import com.application.exceptions.NumParamsException;
import com.application.tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an EditContentCommand
 */
public class EditContentCommandFactory implements CommandFactory {
    private User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public EditContentCommandFactory(User user) {
        this.user = user;
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        EditContentCommand command;
        try {
            command = mapper.convertValue(attributes, EditContentCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("EditContent");
        }

        if (command.getElement() == null || command.getContent() == null){
            throw new NumParamsException("EditContent");
        }

        command.setUser(this.user);
        return command;
    }
}
