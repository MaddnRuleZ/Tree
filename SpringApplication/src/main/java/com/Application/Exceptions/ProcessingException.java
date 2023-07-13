package com.Application.Exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProcessingException extends Exception{
    public ProcessingException(String message) {
        super(message);
    }

    /**
     * generates the Json response String, if execution failed
     */
    public JsonNode generateFailureResponse()   {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("error", this.getMessage());
        return response;
    }

}
