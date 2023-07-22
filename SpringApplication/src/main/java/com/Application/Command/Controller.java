package com.Application.Command;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.GetCommand;
import com.Application.Exceptions.FailureResponse;
import com.Application.Exceptions.ProcessingException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class Controller {
    private final CommandHandler commandHandler;
    private final User user;

    /**
     * Constructor using reflection
     * @param commandHandler handler to process commands
     * @param user user that holds information of LaTeX-Project
     */
    @Autowired
    public Controller(CommandHandler commandHandler, User user) {
        this.commandHandler = commandHandler;
        this.user = user;
    }

    /**
     * Processes a request from the client
     * @param json the request from the client
     * @return the response to the client
     */
    @PostMapping("/api")
    public ResponseEntity<JsonNode> processRequest(@RequestBody JsonNode json) {
        try {
            HttpStatus status;
            JsonNode response = commandHandler.processCommand(json);
            if (commandHandler.isSuccess()) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
            return new ResponseEntity<>(response, status);
        } catch (ProcessingException e) {
            return new ResponseEntity<>(FailureResponse.generateFailureResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Processes a GET-request for the editorView
     * @return the structure for EditorView
     */
    @GetMapping("/LoadFullData")
    public ResponseEntity<JsonNode> processGetEditorRequest() {
        Command command = new GetCommand(user.getRoot(), true);
        HttpStatus status;

        JsonNode response = command.execute();
        if(command.isSuccess()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    /**
     * Processes a GET-request for the treeView
     * @return the structure for treeView
     */
    @GetMapping("/LoadTreeData")
    public ResponseEntity<JsonNode> processGetTreeRequest() {
        Command command = new GetCommand(user.getRoot(), false);
        HttpStatus status;

        JsonNode response = command.execute();
        if(command.isSuccess()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }
}