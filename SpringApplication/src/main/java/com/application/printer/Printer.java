package com.application.printer;

import com.application.User;
import com.application.exceptions.UnknownElementException;
import com.application.tree.elements.roots.Root;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class Printer {
    private String path;
    private User user;

    public static Map<String, StringBuilder> map = new HashMap<>();

    public Printer (User user) {
        this.user = user;
    }

    /**
     * @param path file to export to
     */
    public Printer(String path, User user) {
        this.path = path;
        this.user = user;
    }

    /**
     * exports the tree
     */
    public abstract void export() throws IOException, UnknownElementException;
    public String getPath() {
        return path;
    }
    protected User getUser() {
        return user;
    }
}
