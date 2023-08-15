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

    private static String FIGURE_PATH = "";

    private boolean exportComments = true;
    private boolean exportSummary = true;


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

    public static String getFigurePath() {
        return FIGURE_PATH;
    }

    public static void setFigurePath(String path) {
        FIGURE_PATH = path;
    }

    public boolean isExportComments() {
        return exportComments;
    }

    public void setExportComments(boolean exportComments) {
        this.exportComments = exportComments;
    }

    public boolean isExportSummary() {
        return exportSummary;
    }

    public void setExportSummary(boolean exportSummary) {
        this.exportSummary = exportSummary;
    }
}
