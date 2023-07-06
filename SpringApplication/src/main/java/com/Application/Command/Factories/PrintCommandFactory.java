package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.PrintCommand;
import com.fasterxml.jackson.databind.JsonNode;
import com.Application.TreeX;
import com.Application.Command.CommandTypes.Command;

public class PrintCommandFactory implements CommandFactory {
    //private Printer printer;
    //private Root root;

    public PrintCommandFactory(TreeX treeX) {
        //this.root = treeX.getRoot();
        //this.printer = treeX.getExportPrinter();
    }

    @Override
    public Command createCommand(JsonNode attributes) {
        PrintCommand command = new PrintCommand();


        //TODO
        return command;
    }
}
