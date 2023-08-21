package com.application.printer;

import com.application.User;
import com.application.exceptions.OverleafGitException;
import com.application.exceptions.UnknownElementException;

import java.io.File;
import java.io.IOException;

public abstract class Printer {
    private String path;

    private final User user;

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
    public abstract void export() throws IOException, UnknownElementException, OverleafGitException;
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
        FIGURE_PATH = path.replace("\\", "/");
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
