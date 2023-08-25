package com.application.command;

import com.application.User;
import com.application.command.types.Command;
import com.application.command.types.GetCommand;
import com.application.exceptions.FailureResponse;
import com.application.exceptions.ProcessingException;
import com.application.interpreter.GitWatcher;
import com.application.printer.AutoExport;
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
    private final AutoExport autoExport;

    private final GitWatcher gitWatcher;



    /**
     * Constructor using reflection
     *
     * @param commandHandler handler to process commands
     * @param user           user that holds information of LaTeX-Project
     * @param gitWatcher    watcher to check for changes on download
     */
    @Autowired
    public Controller(CommandHandler commandHandler, User user, AutoExport autoExport, GitWatcher gitWatcher) {
        this.commandHandler = commandHandler;
        this.user = user;
        this.autoExport = autoExport;
        this.gitWatcher = gitWatcher;
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

            printJsonString(response);

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
        Command command = new GetCommand(user, true);
        return getJsonNodeResponseEntity(command);
    }

    /**
     * Processes a GET-request for the treeView
     * @return the structure for treeView
     */
    @GetMapping("/LoadTreeData")
    public ResponseEntity<JsonNode> processGetTreeRequest() {
        Command command = new GetCommand(user, false);
        return getJsonNodeResponseEntity(command);
    }




    /**
     * Processes a GET-request for changes
     * @return if changes happened and if an error occurred
     */
    @GetMapping("/CheckForUpdates")
    public ResponseEntity<JsonNode> processCheckForUpdates() {
        ObjectNode response = new ObjectMapper().createObjectNode();
        HttpStatus status;
        boolean hasChanges = gitWatcher.hasChanges();

        if(hasChanges){
            gitWatcher.setChanges(false);
        }
        response.put("hasUpdates", hasChanges);

        if(gitWatcher.isFailure()) {
            status = HttpStatus.BAD_REQUEST;
            response.put("error", gitWatcher.getFailureMessage());
            gitWatcher.setChanges(false);
            gitWatcher.setFailure(false);
            gitWatcher.setFailureMessage(null);
        } else if(autoExport.isFailure()){
            status = HttpStatus.BAD_REQUEST;
            response.put("error", autoExport.getFailureMessage());
            autoExport.setFailure(false);
            autoExport.setFailureMessage(null);
        } else {
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, status);
    }


    /**
     * creates a ResponseEntity for getData requests
     * @param command the command to be executed
     * @return the ResponseEntity
     */
    private ResponseEntity<JsonNode> getJsonNodeResponseEntity(Command command) {
        HttpStatus status;

        JsonNode response = command.execute();

        printJsonString(response);

        if(command.isSuccess()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }


    /**
     * ForTesting
     * Prints the JsonNode in a readable format
     * @param response the JsonNode to be printed
     */
    private static void printJsonString(JsonNode response) {
        try {
            String jsonString = response.toString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);
            //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
            //System.out.println("\n \n \n \n \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}