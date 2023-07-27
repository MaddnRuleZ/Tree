package com.application.Command.Factories;

import com.application.Command.CommandTypes.AddCommand;
import com.application.Command.CommandTypes.Command;
import com.application.Exceptions.NumParamsException;
import com.application.Tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create an AddCommand
 */
public class AddCommandFactory implements CommandFactory {
    /**
     * root of the LaTeX-Project
     */
    private final Root root;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public AddCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        AddCommand command;
        try {
           command = mapper.convertValue(attributes, AddCommand.class);
        } catch (IllegalArgumentException e) {
            throw new NumParamsException("AddElement");
        }

        if (command.getContent() == null || command.getParent() == null){
            throw new NumParamsException("AddElement");
        }

        command.setRoot(this.root);
        return command;
    }

}
