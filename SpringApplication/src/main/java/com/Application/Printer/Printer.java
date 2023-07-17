package com.Application.Printer;

import com.Application.Tree.elements.Root;

import java.io.IOException;

public abstract class Printer {


    /**
     * exports the tree
     * @return true if successful, false if not
     */
    public abstract boolean export() throws IOException;

}
