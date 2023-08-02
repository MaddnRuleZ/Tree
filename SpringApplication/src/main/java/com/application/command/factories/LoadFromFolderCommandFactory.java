package com.application.command.factories;

import com.application.command.types.Command;
import com.application.command.types.LoadFromFolderCommand;
import com.application.exceptions.NumParamsException;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
/*
    command has to be read manually due to problems with spring and using reflection
 */
/**
 * Factory to create an LoadFromFolderCommand
 */
public class LoadFromFolderCommandFactory implements CommandFactory {
    private User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public LoadFromFolderCommandFactory(User user) {
        this.user = user;
    }


    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException {
        LoadFromFolderCommand command = new LoadFromFolderCommand();
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = attributes.fields();
        if(!fieldsIterator.hasNext()) {
            throw new NumParamsException("LoadFromFolder - missing parameter");
        }
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
        return command;
    }
}
