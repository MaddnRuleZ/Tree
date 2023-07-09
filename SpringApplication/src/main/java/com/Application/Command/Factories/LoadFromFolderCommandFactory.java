package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.LoadFromFolderCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadFromFolderCommandFactory implements CommandFactory {
    private final User user;

    public LoadFromFolderCommandFactory(User user) {
        this.user = user;
    }
    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        LoadFromFolderCommand command = mapper.convertValue(attributes, LoadFromFolderCommand.class);

        if (command.getPath() == null){
            throw new NumParamsException("Missing Parameter in LoadFromFolderCommand");
        }

        command.setUser(this.user);
        return command;
    }
}
