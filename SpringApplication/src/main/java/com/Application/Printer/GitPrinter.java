package com.Application.Printer;

import com.Application.Exceptions.UnknownElementException;
import com.Application.User;

import java.io.IOException;

public class GitPrinter extends Printer {
    /**
     *  user that holds information of LaTeX-Project
     */
    private User user;
    /**
     * url to the git repository
     */
    private String url;
    /**
     * username for the git repository
     */
    private String username;
    /**
     * password for the git repository
     */
    private String password;

    /**
     * path to the folder where the git repository should be cloned
     */
    private String path;

    public GitPrinter(User user, String url, String username, String password, String path) {
        this.user = user;
        this.url = url;
        this.username = username;
        this.password = password;
        this.path = path;

    }

    @Override
    public void export() throws IOException, UnknownElementException {
        //TODO

    }
}
