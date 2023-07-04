package SpringApplication.Command.Factories;

public interface CommandFactory {
    
    public abstract Command createCommand(JsonObject attributes);
}
