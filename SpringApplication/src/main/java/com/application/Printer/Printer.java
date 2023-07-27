package com.application.Printer;

import com.application.Exceptions.UnknownElementException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Printer {

    private String path;

    public static Map<String, StringBuilder> map = new HashMap<>();

    /**
     * exports the tree
     */
    public abstract void export() throws IOException, UnknownElementException;

}
