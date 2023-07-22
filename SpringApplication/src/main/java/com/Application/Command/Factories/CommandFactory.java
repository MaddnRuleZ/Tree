package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Exceptions.NumParamsException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Factory to create a command
 * @see com.Application.Command.CommandTypes.Command
 */
public interface CommandFactory {

    /**
     * creates a command from a JsonNode, sets the attributes of command
     * @param attributes attributes of command that has to be mapped
     * @return created command
     * @throws NumParamsException if the number of parameters is not correct
     * @throws IllegalArgumentException if mapping of attributes to command is not possible
     */
    Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException;
}
