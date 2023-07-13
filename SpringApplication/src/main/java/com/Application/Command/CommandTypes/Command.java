package com.Application.Command.CommandTypes;

import com.fasterxml.jackson.databind.JsonNode;

public interface Command {
    /**
     * executes the command
     */
    JsonNode execute();


    /**
     * generates the Json response String, if execution failed
     * @return failureResponse
     */
    default JsonNode generateFailureResponse(String errorMessage) {

        //TODO
        return null;
    }

    JsonNode generateResponse();
}
