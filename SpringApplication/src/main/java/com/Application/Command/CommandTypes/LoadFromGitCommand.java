package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.User;

public class LoadFromGitCommand implements Command, IEditorResponse, ILocks {
   private User user;
   private String url;
   private String username;
   private String password;

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

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
