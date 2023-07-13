package com.Application.Command.CommandTypes.Interfaces;

import com.Application.Exceptions.GeneratingResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IEditorResponse {
    default JsonNode generateResponse() throws JsonProcessingException {
        //TODO generate response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode response = mapper.readTree("Response: Platzhalter");
        return response;
    }
}
