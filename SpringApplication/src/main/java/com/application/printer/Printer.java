package com.application.printer;

import com.application.exceptions.UnknownElementException;
import com.application.tree.elements.roots.Root;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Printer {

    private String path;


    private Root root;

    public static Map<String, StringBuilder> map = new HashMap<>();

    public Printer (Root root) {
        this.root = root;
    }

    /**
     * @param path file to export to
     * @param root tree structure to be exported
     */
    public Printer(String path, Root root) {
        this.path = path;
        this.root = root;
    }


    /**
     * exports the tree
     */
    public abstract void export() throws IOException, UnknownElementException;

    public String getPath() {
        return path;
    }

    public Root getRoot() {
        return root;
    }

}
