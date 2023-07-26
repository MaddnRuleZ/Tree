package com.Application.Command.CommandTypes;


import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Printer.Printer;
import com.fasterxml.jackson.databind.JsonNode;


//TODO
/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class PrintCommand extends Command {
    /**
     * printer that holds information of LaTeX-Project
     */
    private Printer printer;

    public PrintCommand(Printer printer) {
        this.printer = printer;
    }

    //TODO
    @Override
    public JsonNode execute() {
        try {
            acquireStructureReadLock();
            this.printer.export();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseStructureReadLock();
        }
        return null;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
