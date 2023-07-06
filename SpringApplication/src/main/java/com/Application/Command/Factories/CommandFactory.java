package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Exceptions.NumParamsException;
import com.fasterxml.jackson.databind.JsonNode;

public interface CommandFactory {
    
    Command createCommand(JsonNode attributes) throws NumParamsException;
}
