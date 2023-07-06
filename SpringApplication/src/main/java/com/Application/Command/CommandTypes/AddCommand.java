package main.java.com.Application.Command.CommandTypes;

import java.util.UUID;

public class AddCommand extends Command {
    private String type;
    private UUID parent;
    private UUID previousChild;
    private String content;
    //private Root root;

    @Override
    public void execute() {
        //TODO
    }
}
