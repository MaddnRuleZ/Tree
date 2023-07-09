package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Printer.FilePrinter;
import com.Application.User;

public class LoadFromFolderCommand implements Command, IEditorResponse {
    private User user;
    private String path;

    @Override
    public String execute() {
        //TODO Parsing
        FilePrinter printer = new FilePrinter(path, user.getRoot());
        return null;
    }

    @Override
    public String generateResponse() {
        return null;
    }

    public User getUser() {
        return user;
    }

    public String getPath() {
        return path;
    }


    public void setUser(User user) {
        this.user = user;
    }
}
