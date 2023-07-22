package com.Application.Command.Factories;

import com.Application.Command.CommandTypes.Command;
import com.Application.Command.CommandTypes.PrintCommand;
import com.Application.Printer.Printer;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;

public class PrintCommandFactory implements CommandFactory {
    /**
     * printer of the LaTeX-Project
     */
    private final Printer printer;

    /**
     * Constructor
     * @param user user that holds information of LaTeX-Project
     */
    public PrintCommandFactory(User user) {
        this.printer = user.getPrinter();
    }

    @Override
    public Command createCommand(JsonNode attributes) {
        PrintCommand command = new PrintCommand();
        command.setPrinter(this.printer);

        return command;
    }
}
