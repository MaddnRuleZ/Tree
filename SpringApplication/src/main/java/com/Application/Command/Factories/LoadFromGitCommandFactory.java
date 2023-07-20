package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.LoadFromGitCommand;
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
 *  Factory to create an LoadFromGitCommand
 */
public class LoadFromGitCommandFactory implements CommandFactory {
    private final User user;

    public LoadFromGitCommandFactory(User user) {
        this.user = user;
    }
    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        LoadFromGitCommand command = new LoadFromGitCommand();
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = attributes.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            String value = field.getValue().asText();
            switch (field.getKey()) {
                case "url":
                    command.setUrl(value);
                    break;
                case "username":
                    command.setUsername(value);
                    break;
                case "password":
                    command.setPassword(value);
                    break;
                default:
                    throw new IllegalArgumentException("LoadFromGit - unknown parameter");
            }
        }

        if (command.getUrl() == null || command.getUsername() == null || command.getPassword() == null ){
            throw new NumParamsException("LoadFromGit - parameter not set");
        }

        command.setUser(this.user);
        return command;
    }
}
