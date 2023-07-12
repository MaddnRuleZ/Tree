package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.EditCommentCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.sectioning.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EditCommentCommandFactory implements CommandFactory {
    private final Root root;

    public EditCommentCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        EditCommentCommand command = mapper.convertValue(attributes, EditCommentCommand.class);

        if (command.getElement() == null || command.getComment() == null){
            throw new NumParamsException("Missing Parameter in EditCommentCommand");
        }

        command.setRoot(this.root);
        return command;
    }
}
