package main.java.com.Application.Command.Factories;

import main.java.com.Application.Command.CommandTypes.Command;
import main.java.com.Application.Command.CommandTypes.PrintCommand;
import main.java.com.Application.TreeX;

public class PrintCommandFactory implements CommandFactory {
    private Printer printer;
    private Root root;

    public PrintCommandFactory(TreeX treeX) {
        this.root = treeX.getRoot();
        this.printer = treeX.getExportPrinter();
    }

    @Override
    public Command createCommand(JsonNode attributes) {
        PrintCommand command = new PrintCommand();
        //TODO
    }
}
