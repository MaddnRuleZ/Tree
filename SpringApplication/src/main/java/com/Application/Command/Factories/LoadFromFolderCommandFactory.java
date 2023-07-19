package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.LoadFromFolderCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an LoadFromFolderCommand
 */
public class LoadFromFolderCommandFactory implements CommandFactory {
    private final User user;

    public LoadFromFolderCommandFactory(User user) {
        this.user = user;
    }
    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException {
        ObjectMapper mapper = new ObjectMapper();
        LoadFromFolderCommand command;
        //try {
           command  = mapper.convertValue(attributes, LoadFromFolderCommand.class);
        /*} catch (IllegalArgumentException e) {
            throw new NumParamsException("LoadFromFolder - mapping failed");
        }*/
        if (command.getPath() == null){
            throw new NumParamsException("LoadFromFolder - parameter not set");
        }

        command.setUser(this.user);
        return command;
    }
}
