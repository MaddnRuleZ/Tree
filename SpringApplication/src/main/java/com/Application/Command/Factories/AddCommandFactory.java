package main.java.com.Application.Command.Factories;

import main.java.com.Application.Command.CommandTypes.Command;
import main.java.com.Application.TreeX;

public class AddCommandFactory implements CommandFactory {
    private Root root;

    public AddCommandFactory(TreeX treeX) {
        this.root = treeX.getRoot();
    }
    @Override
    public Command createCommand(JsonNode attributes) {
        //TODO
    }
}
