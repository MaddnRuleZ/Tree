package com.Application.Command.CommandTypes;


import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Printer.Printer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class PrintCommand implements Command, ILocks {
    private Printer printer;


    //TODO Fehlerbehandlung
    @Override
    public JsonNode execute() {
        try {
            acquireStructureReadLock();
            printer.export();
        } catch (IOException e) {
            releaseStructureReadLock();
            return null;
        }
        releaseStructureReadLock();
        return generateResponse();
    }


    public JsonNode generateResponse() {
        //TODO
        return null;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
