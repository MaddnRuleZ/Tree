package com.application.command.factories;

import com.application.User;
import com.application.command.types.Command;
import com.application.command.types.EditSummaryCommand;
import com.application.exceptions.NumParamsException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an EditSummaryCommand
 */
public class EditSummaryCommandFactory implements CommandFactory{
    private final User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public EditSummaryCommandFactory(User user) {
        this.user = user;
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

        command.setUser(this.user);
        return command;
    }
}
