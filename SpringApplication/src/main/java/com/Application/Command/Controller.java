package com.Application.Command;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.GetCommand;
import com.Application.Exceptions.FailureResponse;
import com.Application.Exceptions.ProcessingException;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
            String jsonString = response.toString();

            String formattedJsonString = formatJsonString(jsonString);
            System.out.println(formattedJsonString);
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

    /**
     * Processes a GET-request for changes
     * @return if changes happened on remote repository
     */
    @GetMapping("/checkForUpdates")
    public ResponseEntity<JsonNode> processCheckForUpdates() {
        ObjectNode response = new ObjectMapper().createObjectNode();
        HttpStatus status;
        response.put("hasUpdates", user.getGitWatcher().hasChanges());
        if(user.getGitWatcher().isFailure()) {
            status = HttpStatus.BAD_REQUEST;
            response.put("error", user.getGitWatcher().getFailureMessage());
        } else if(user.getClock().isFailure()){
            status = HttpStatus.BAD_REQUEST;
            response.put("error", user.getClock().getFailureMessage());
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response, status);
    }

    //@GetMapping("/Export")
    //TODO: Export
    public ResponseEntity<JsonNode> processExportRequest() {
        return null;
    }

    private static String formatJsonString(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}