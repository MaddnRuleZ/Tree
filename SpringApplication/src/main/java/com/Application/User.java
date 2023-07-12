package com.Application;

import com.Application.Interpreter.Parser;
import com.Application.Printer.Printer;
import com.Application.Tree.elements.sectioning.Root;
import org.springframework.stereotype.Component;

/**
 * holds the tree structure and classes responsible for translating
 * the structure in different formats
 */
@Component
public class User {
    /**
     * starting point of the tree structure
     */
    private Root root;
    /**
     * tree structure to LaTeX-Code printer
     */
    private Printer printer;
    /**
     * LaTeX-Code to tree structure parser
     */
    private Parser parser;

    public User() {
        //this.root = Root.getInstance(); read Root
    }

    public void setPrinter(Printer Printer) {
        this.printer = Printer;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public Root getRoot() {
        return root;
    }

    public Printer getPrinter() {
        return printer;
    }

    public Parser getParser() {
        return parser;
    }

}
