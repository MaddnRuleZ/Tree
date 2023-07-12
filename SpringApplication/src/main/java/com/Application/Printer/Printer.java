package com.Application.Printer;

import com.Application.Tree.elements.sectioning.Root;

import java.io.IOException;

public abstract class Printer {

    public String toLaTeX(Root root) {
        //TODO
        return null;
    }

    /**
     * exports the tree
     * @return true if successful, false if not
     */
    public abstract boolean export() throws IOException;

}
