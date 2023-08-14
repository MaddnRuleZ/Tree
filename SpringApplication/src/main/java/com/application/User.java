package com.application;

import com.application.interpreter.Parser;
import com.application.printer.AutoExport;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import org.springframework.stereotype.Component;

/**
 * holds the tree structure and
 * classes responsible for translating the structure in different formats
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

    private AutoExport autoExport;

    public User() {
        this.root = Root.getInstance();
    }

    public void resetUser(){
        this.printer = null;
        Root.resetInstance();
    }

    public void setPrinter(Printer Printer) {
        this.printer = Printer;
    }

    public Root getRoot() {
        return root;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

}
