package main.java.com.Application.Command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

    private final CommandHandler commandHandler;

    @Autowired
    public ApiController(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processRequest(@RequestBody JsonNode json) {
        commandHandler.processJson(json);

        //TODO: createResponse
    }

    private JsonNode createResponseJson() {
        // TODO
        return null;
    }
}