package com.application.command;

import com.application.command.types.Command;
import com.application.command.types.ExportCommand;
import com.application.command.types.GetCommand;
import com.application.exceptions.FailureResponse;
import com.application.exceptions.ProcessingException;
import com.application.interpreter.GitWatcher;
import com.application.printer.AutoExport;
import com.application.User;
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
     * @param gitWatcher watcher to check for errors on download
     * @param autoExport autoExport to check for errors on upload
     * @return if changes happened and if an error occurred
     */
    @GetMapping("/checkForUpdates")
    public ResponseEntity<JsonNode> processCheckForUpdates(GitWatcher gitWatcher, AutoExport autoExport) {
        ObjectNode response = new ObjectMapper().createObjectNode();
        HttpStatus status;

        printJsonString(response);

        response.put("hasUpdates", gitWatcher.hasChanges());
        if(gitWatcher.isFailure()) {
            status = HttpStatus.BAD_REQUEST;
            response.put("error", gitWatcher.getFailureMessage());
        } else if(autoExport.isFailure()){
            status = HttpStatus.BAD_REQUEST;
            response.put("error", autoExport.getFailureMessage());
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response, status);
    }

    /**
     * Processes a GET-request for the export
     * @return if export was successful
     */
    @GetMapping("/Export")
    public ResponseEntity<JsonNode> processExportRequest() {
        Command command = new ExportCommand(user.getPrinter());
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
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
            System.out.println("\n \n \n \n \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}