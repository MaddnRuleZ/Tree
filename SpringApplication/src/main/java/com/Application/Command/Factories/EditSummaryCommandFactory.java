package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.EditSummaryCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.parents.sectioning.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            throw new NumParamsException("Missing Parameter in EditSummaryCommand");
        }

        command.setRoot(this.root);
        return command;
    }
}
