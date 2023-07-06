package com.Application.Command;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final CommandHandler commandHandler = new CommandHandler();

    @PostMapping("/process")
    public ResponseEntity<JsonNode> processRequest(@RequestBody JsonNode json) {
        return new ResponseEntity<>(HttpStatus.OK);
        /*
        try {
            JsonNode response = commandHandler.processCommand(json);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnrecognizedCommandException | NumParamsException e) {
            JsonNode errorResponse = null; //TODO write ErrorResponse
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        */

    }

}