package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.MoveElementEditorCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.root.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an MoveElementEditorCommand
 */
public class MoveElementEditorCommandFactory implements CommandFactory {
    private final Root root;

    public MoveElementEditorCommandFactory(User user) {
        this.root = user.getRoot();
    }
    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        MoveElementEditorCommand command;
        try {
            command = mapper.convertValue(attributes, MoveElementEditorCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("MoveElementEditor - mapping failed");
        }

        if (command.getElement() == null || command.getNewParent() == null || command.getPreviousElement() == null ){
            throw new NumParamsException("MoveElementEditor - parameter not set");
        }

        command.setRoot(this.root);
        return command;
    }
}
