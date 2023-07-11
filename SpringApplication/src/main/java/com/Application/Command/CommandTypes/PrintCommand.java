package com.Application.Command.CommandTypes;


import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Printer.Printer;
import com.Application.Tree.elements.Root;

/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class PrintCommand implements Command, ILocks {
    private Printer printer;


    @Override
    public String execute() {
        //TODO
        return null;
    }

    @Override
    public String generateResponse() {
        return null;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
