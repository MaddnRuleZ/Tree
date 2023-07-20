package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.LoadFromFolderCommand;
import com.Application.Exceptions.NumParamsException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Map;
/*
    Command has to be read manually due to problems with spring and using reflection
 */
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
        LoadFromFolderCommand command = new LoadFromFolderCommand();
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = attributes.fields();
        Map.Entry<String, JsonNode> field = fieldsIterator.next();
        String path = field.getValue().asText();
        command.setPath(path);

        if(fieldsIterator.hasNext()){
            throw new NumParamsException("LoadFromFolder - too many parameters");
        }

        if (command.getPath() == null){
            throw new NumParamsException("LoadFromFolder - parameter not set");
        }

        command.setUser(this.user);
        command.setRoot(this.user.getRoot());
        return command;
    }
}
