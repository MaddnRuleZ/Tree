package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.MoveElementEditorCommand;
import com.application.exceptions.NumParamsException;
import com.application.tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an MoveElementEditorCommand
 */
public class MoveElementEditorCommandFactory implements CommandFactory {
    private User user;
    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public MoveElementEditorCommandFactory(User user) {
        this.user = user;
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

        command.setUser(this.user);
        return command;
    }
}
