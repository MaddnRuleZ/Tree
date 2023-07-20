package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.EditCommentCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.roots.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  Factory to create an EditCommentCommand
 */
public class EditCommentCommandFactory implements CommandFactory {
    /**
     * root of the LaTeX-Project
     */
    private final Root root;

    public EditCommentCommandFactory(User user) {
        this.root = user.getRoot();
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

        command.setRoot(this.root);
        return command;
    }
}
