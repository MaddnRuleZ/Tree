package com.application.command.types;


import com.application.printer.Printer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class ExportCommand extends Command {
    /**
     * printer that holds information of LaTeX-Project
     */
    private Printer printer;

    public ExportCommand(Printer printer) {
        this.printer = printer;
    }

    @Override
    public JsonNode execute() {
        try {
            acquireStructureReadLock();
            this.printer.export();
            this.setSuccess(true);
        } catch (Exception e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureReadLock();

        }
        return generateSuccessResponse();
    }


    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
