package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.PrintCommand;
import com.Application.Printer.Printer;
import com.Application.Tree.elements.Root;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;

public class PrintCommandFactory implements CommandFactory {
    private final Printer printer;
    private final Root root;

    public PrintCommandFactory(User user) {
        this.root = user.getRoot();
        this.printer = user.getPrinter();
    }

    @Override
    public Command createCommand(JsonNode attributes) {
        PrintCommand command = new PrintCommand();
        command.setPrinter(this.printer);
        command.setRoot(this.root);

        return command;
    }
}
