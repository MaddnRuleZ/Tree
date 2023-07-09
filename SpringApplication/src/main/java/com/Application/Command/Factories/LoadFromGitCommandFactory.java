package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.LoadFromGitCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadFromGitCommandFactory implements CommandFactory {
    private final User user;

    public LoadFromGitCommandFactory(User user) {
        this.user = user;
    }
    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        LoadFromGitCommand command = mapper.convertValue(attributes, LoadFromGitCommand.class);

        if (command.getUrl() == null || command.getUsername() == null || command.getPassword() == null ){
            throw new NumParamsException("Missing Parameter in LoadFromGitCommand");
        }

        command.setUser(this.user);
        return command;
    }
}
