package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.EditCommentCommand;
import com.application.exceptions.NumParamsException;
import com.application.tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  Factory to create an EditCommentCommand
 */
public class EditCommentCommandFactory implements CommandFactory {

    private User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public EditCommentCommandFactory(User user) {
        this.user = user;
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        EditCommentCommand command;
        try {
            command = mapper.convertValue(attributes, EditCommentCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("EditComment");
        }

        if (command.getElement() == null || command.getComment() == null){
            throw new NumParamsException("EditComment");
        }

        command.setUser(this.user);
        return command;
    }
}
