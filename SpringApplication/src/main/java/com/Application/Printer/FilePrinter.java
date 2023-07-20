package com.Application.Printer;

import com.Application.Tree.elements.roots.Root;

import java.io.IOException;

public class FilePrinter extends Printer {
    private final String path;
    private final Root root;

    public FilePrinter(String currentFile, Root root) {
        this.path = currentFile;
        this.root = root;
    }

    public boolean export() throws IOException {
        //TODO
        return false;
    }

}
