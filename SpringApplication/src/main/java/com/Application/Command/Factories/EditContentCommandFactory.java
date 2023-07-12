package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.EditContentCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.sectioning.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EditContentCommandFactory implements CommandFactory {
    private final Root root;

    public EditContentCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        EditContentCommand command = mapper.convertValue(attributes, EditContentCommand.class);

        if (command.getElement() == null || command.getContent() == null){
            throw new NumParamsException("Missing Parameter in EditContentCommand");
        }

        command.setRoot(this.root);
        return command;
    }
}
