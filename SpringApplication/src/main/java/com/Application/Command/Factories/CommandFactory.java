package main.java.com.Application.Command.Factories;

import main.java.com.Application.Command.CommandTypes.Command;
import com.fasterxml.jackson.databind.JsonNode;

public interface CommandFactory {
    
    Command createCommand(JsonNode attributes);
}
