package com.application.Exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Interface for generating a failure response
 */
public interface FailureResponse {
    /**
     * generates the Json response String, if execution failed
     * @return failureResponse
     */
    static JsonNode generateFailureResponse(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("error", message);
        return rootNode;
    }
}
