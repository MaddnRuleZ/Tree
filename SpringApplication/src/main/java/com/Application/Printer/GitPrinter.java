package com.Application.Printer;

import com.Application.User;

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

    public GitPrinter(User user, String url, String username, String password) {
        this.user = user;
        this.url = url;
        this.username = username;
        this.password = password;

    }

    @Override
    public boolean export() {
        //TODO
        return false;
    }
}
