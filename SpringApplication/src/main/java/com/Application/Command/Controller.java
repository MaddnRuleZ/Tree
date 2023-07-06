package com.Application.Command;

import com.fasterxml.jackson.databind.JsonNode;
import com.Application.Exceptions.NumParamsException;
import com.Application.Exceptions.UnrecognizedCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    private final CommandHandler commandHandler;

    @Autowired
    public Controller(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping("/")
    public ResponseEntity<JsonNode> processRequest(@RequestBody JsonNode json) {
        try {
            JsonNode response = commandHandler.processCommand(json);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnrecognizedCommandException | NumParamsException e) {
            JsonNode errorResponse = null; //TODO write ErrorResponse
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}