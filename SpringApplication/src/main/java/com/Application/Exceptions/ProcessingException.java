package com.Application.Exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProcessingException extends Exception{
    public ProcessingException(String message) {
        super(message);
    }

}
