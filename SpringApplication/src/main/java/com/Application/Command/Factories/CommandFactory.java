package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Exceptions.NumParamsException;
import com.fasterxml.jackson.databind.JsonNode;

public interface CommandFactory {

    /**
     * creates a coomand from a JSONNOde, sets the attributes of command
     * @param attributes
     * @return created command
     * @throws NumParamsException
     * @throws IllegalArgumentException
     */
    Command createCommand(JsonNode attributes) throws NumParamsException, IllegalArgumentException;
}
