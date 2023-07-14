package com.Application.Command.CommandTypes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Command {
    /**
     * executes the command
     */
    JsonNode execute(boolean success);


    /**
     * generates the Json response String, if execution failed
     * @return failureResponse
     */
    default JsonNode generateFailureResponse(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("error", message);
        return rootNode;
    }

    JsonNode generateResponse(boolean success, String message);
}
