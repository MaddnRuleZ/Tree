package com.Application.Command.CommandTypes.Interfaces;

import com.Application.Exceptions.GeneratingResponseException;
import com.Application.Exceptions.ProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IEditorResponse {
    default JsonNode generateResponse() throws JsonProcessingException {
        //TODO generate response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("Response: ","Platzhalter");
        return rootNode;
    }
}
