package com.application.command.factories;

import com.application.User;
import com.application.command.types.Command;
import com.application.command.types.MoveElementCommand;
import com.application.exceptions.NumParamsException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an MoveElementEditorCommand
 */
public class MoveElementEditorCommandFactory implements CommandFactory {
    private final User user;
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
        MoveElementCommand command;
        try {
            command = mapper.convertValue(attributes, MoveElementCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("MoveElementEditor - mapping failed");
        }

        if (command.getElement() == null){
            throw new NumParamsException("MoveElementEditor - parameter not set");
        }

        command.setUser(this.user);
        command.setEditor(true);
        return command;
    }
}
