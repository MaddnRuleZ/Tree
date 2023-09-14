package com.application.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonNode> handleException(Exception ex) {
        // Customize the error response here
        System.out.println("An Internal Server Error occurred: " + ex.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("error", "Es ist ein interner Serverfehler aufgetreten. Bitte überprüfen Sie die Ausgabe auf der Console.");

        return new ResponseEntity<>(rootNode, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
