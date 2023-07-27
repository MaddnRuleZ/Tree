package com.application.Command.Factories;

import com.application.Command.CommandTypes.Command;
import com.application.Command.CommandTypes.EditContentCommand;
import com.application.Exceptions.NumParamsException;
import com.application.Tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an EditContentCommand
 */
public class EditContentCommandFactory implements CommandFactory {
    /**
     * root of the LaTeX-Project
     */
    private final Root root;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
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
