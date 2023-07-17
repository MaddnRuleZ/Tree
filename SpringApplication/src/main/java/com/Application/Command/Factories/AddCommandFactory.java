package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.AddCommand;
import com.Application.Command.CommandTypes.Command;
import com.Application.Exceptions.NumParamsException;
import com.Application.Tree.elements.Root;
import com.Application.User;
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


    public AddCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        AddCommand command = mapper.convertValue(attributes, AddCommand.class);

        if (command.getContent() == null || command.getParent() == null || command.getPreviousChild() == null ){
            throw new NumParamsException("AddElement");
        }

        command.setRoot(this.root);
        return command;
    }

}
