package com.Application.Command.CommandTypes;


import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Printer.Printer;
import com.fasterxml.jackson.databind.JsonNode;


//TODO Klasse löschen ?
/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class PrintCommand extends Command {
    /**
     * printer that holds information of LaTeX-Project
     */
    private Printer printer;


    @Override
    public JsonNode execute() {
        return null;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
