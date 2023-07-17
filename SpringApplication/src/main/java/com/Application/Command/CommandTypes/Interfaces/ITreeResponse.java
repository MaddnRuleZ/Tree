package com.Application.Command.CommandTypes.Interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Interface that generates a response for the tree
 */
public interface ITreeResponse {
    /**
     * Generates a response for the tree
     * @return response for the tree
     * @throws JsonProcessingException
     */
    default JsonNode generateResponse() throws JsonProcessingException {
        //TODO
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("Response: ","Platzhalter");
        return rootNode;
    }
}
