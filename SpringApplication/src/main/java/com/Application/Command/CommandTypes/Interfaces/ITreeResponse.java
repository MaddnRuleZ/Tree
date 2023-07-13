package com.Application.Command.CommandTypes.Interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ITreeResponse {
    default JsonNode generateResponse() throws JsonProcessingException {
        //TODO
        ObjectMapper mapper = new ObjectMapper();
        JsonNode response = mapper.readTree("Response: Platzhalter");
        return response;
    }
}
