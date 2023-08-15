package com.application.command.factories;

import com.application.User;
import com.application.command.types.Command;
import com.application.command.types.EditSummaryCommand;
import com.application.command.types.ExportCommand;
import com.application.exceptions.NumParamsException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExportCommandFactory implements CommandFactory {

    private User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public ExportCommandFactory(User user) {
        this.user = user;
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        ExportCommand command;
        try {
            command = mapper.convertValue(attributes, ExportCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("Export");
        }


        command.setUser(this.user);
        return command;
    }
}
