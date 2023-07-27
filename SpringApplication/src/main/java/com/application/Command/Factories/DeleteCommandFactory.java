package com.application.Command.Factories;

import com.application.Command.CommandTypes.Command;
import com.application.Command.CommandTypes.DeleteElementCommand;
import com.application.Exceptions.NumParamsException;
import com.application.Tree.elements.roots.Root;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory to create a DeleteCommand
 */
public class DeleteCommandFactory implements CommandFactory {
    /**
     * root of the LaTeX-Project
     */
    private final Root root;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public DeleteCommandFactory(User user) {
        this.root = user.getRoot();
    }

    @Override
    public Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        DeleteElementCommand command;
        try {
            command = mapper.convertValue(attributes, DeleteElementCommand.class);
        } catch (IllegalArgumentException e){
            throw new NumParamsException("DeleteElement");
        }
        // throws only an Exception if the element is null, not if cascading missing in jsonFile
        // default value of cascading is false
        if (command.getElement() == null){
            throw new NumParamsException("DeleteElement");
        }

        command.setRoot(this.root);
        return command;
    }
}
