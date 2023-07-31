package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.EditSummaryCommand;
import com.application.exceptions.NumParamsException;
import com.application.tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an EditSummaryCommand
 */
public class EditSummaryCommandFactory implements CommandFactory{
    /**
     * root of the LaTeX-Project
     */
    private final Root root;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public EditSummaryCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        EditSummaryCommand command;
        try {
            command = mapper.convertValue(attributes, EditSummaryCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("EditSummary");
        }

        if (command.getElement() == null || command.getSummary() == null){
            throw new NumParamsException("EditSummary");
        }

        command.setRoot(this.root);
        return command;
    }
}