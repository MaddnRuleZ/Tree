package com.Application.Command.CommandTypes;


import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Printer.Printer;

import java.io.IOException;

/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class PrintCommand implements Command, ILocks {
    private Printer printer;


    @Override
    public String execute() {
        try {
            acquireStructureReadLock();
            printer.export();
        } catch (IOException e) {
            releaseStructureReadLock();
            return "Error: " + e.getMessage();
        }
        releaseStructureReadLock();
        return generateResponse();
    }


    public String generateResponse() {
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
