package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.EditSummaryCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an EditSummaryCommand
 */
public class EditSummaryCommandFactory implements CommandFactory{
    private final Root root;

    public EditSummaryCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        EditSummaryCommand command = mapper.convertValue(attributes, EditSummaryCommand.class);

        if (command.getElement() == null || command.getSummary() == null){
            throw new NumParamsException("EditSummary");
        }

        command.setRoot(this.root);
        return command;
    }
}
