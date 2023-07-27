package com.application.Command.Factories;

import com.application.Command.CommandTypes.Command;
import com.application.Command.CommandTypes.LoadFromGitCommand;
import com.application.Exceptions.NumParamsException;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;


/*
    Command has to be read manually due to problems with spring and using reflection
 */
/**
 *  Factory to create an LoadFromGitCommand
 */
public class LoadFromGitCommandFactory implements CommandFactory {
    /**
     * user that holds information of LaTeX-Project
     */
    private final User user;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
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
                case "url" -> command.setUrl(value);
                case "username" -> command.setUsername(value);
                case "password" -> command.setPassword(value);
                default -> throw new IllegalArgumentException("LoadFromGit - unknown parameter");
            }
        }

        if (command.getUrl() == null || command.getUsername() == null || command.getPassword() == null ){
            throw new NumParamsException("LoadFromGit - parameter not set");
        }

        command.setUser(this.user);
        command.setRoot(this.user.getRoot());
        return command;
    }
}
