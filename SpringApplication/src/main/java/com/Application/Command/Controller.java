package com.Application.Command;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ITreeResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class Controller {
    private CommandHandler commandHandler;

    @Autowired
    public Controller(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    /**
     * Processes a request from the client
     * @param json the request from the client
     * @return the response to the client
     */
    @PostMapping("/process")
    public ResponseEntity<JsonNode> processRequest(@RequestBody JsonNode json) {
        return new ResponseEntity<>(HttpStatus.OK);
        /*
        try {
            JsonNode response = commandHandler.processCommand(json);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnrecognizedCommandException | NumParamsException | IllegalArgumentException e) {
            JsonNode errorResponse = "Error: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        */

    }

    /**
     * Processes a GET-request for the editorView
     * @return the structure for EditorView
     */
    @GetMapping("/LoadFullData")
    public ResponseEntity<JsonNode> processGetEditorRequest() {
        return new ResponseEntity<>(HttpStatus.OK);
        /* FEhler implementing Interfaces und Locking
        try {
            JsonNode response = IEditorResponse.super.generateResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            JsonNode errorResponse = "Error: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        */

    }

    /**
     * Processes a GET-request for the treeView
     * @return the structure for treeView
     */
    @GetMapping("/LoadTreeData")
    public ResponseEntity<JsonNode> processGetTreeRequest() {
        return new ResponseEntity<>(HttpStatus.OK);
        /* FEhler implementing Interfaces und Locking
        try {
            JsonNode response = ITreeResponse.super.generateResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            JsonNode errorResponse = "Error: " + e.getMessage();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        */

    }
}