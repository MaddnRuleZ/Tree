package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.EditContentCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.root.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an EditContentCommand
 */
public class EditContentCommandFactory implements CommandFactory {
    private final Root root;

    public EditContentCommandFactory(User user) {
        this.root = user.getRoot();
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

        command.setRoot(this.root);
        return command;
    }
}
