package com.Application.Command.CommandTypes;


import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Printer.Printer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class PrintCommand extends Command {
    private Printer printer;


    @Override
    public JsonNode execute() {
        return null;
    }

    //TODO Fehlerbehandlung
    @Override
    public JsonNode generateResponse() {
        JsonNode response = null;

        return response;
    }


    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
