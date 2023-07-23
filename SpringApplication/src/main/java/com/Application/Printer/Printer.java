package com.Application.Printer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Printer {

    private String path;

    public static Map<String, StringBuilder> map = new HashMap<>();

    /**
     * exports the tree
     * @return true if successful, false if not
     */
    public abstract boolean export() throws IOException;

}
