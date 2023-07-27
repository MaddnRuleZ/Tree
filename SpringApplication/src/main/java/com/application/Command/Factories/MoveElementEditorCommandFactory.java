package com.application.Command.Factories;

import com.application.Command.CommandTypes.Command;
import com.application.Command.CommandTypes.MoveElementEditorCommand;
import com.application.Exceptions.NumParamsException;
import com.application.Tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an MoveElementEditorCommand
 */
public class MoveElementEditorCommandFactory implements CommandFactory {
    /**
     * root of the LaTeX-Project
     */
    private final Root root;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
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
