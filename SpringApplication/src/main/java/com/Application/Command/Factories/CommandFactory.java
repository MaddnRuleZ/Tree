package main.java.com.Application.Command.Factories;

public interface CommandFactory {
    
    public abstract Command createCommand(JsonObject attributes);
}
