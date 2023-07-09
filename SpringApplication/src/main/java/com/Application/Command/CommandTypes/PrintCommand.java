package com.Application.Command.CommandTypes;


import com.Application.Printer.Printer;
import com.Application.Tree.elements.Root;

/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class PrintCommand implements Command {
    private Root root;
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

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
