package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.User;

public class LoadFromFolderCommand implements Command, IEditorResponse, ILocks {
    private User user;
    private String path;

    @Override
    public String execute() {
        //TODO
        return generateResponse();
    }

    @Override
    public String generateResponse() {
        return IEditorResponse.super.generateResponse();
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
