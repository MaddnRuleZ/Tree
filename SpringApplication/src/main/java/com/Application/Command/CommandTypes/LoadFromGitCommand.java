package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;

public class LoadFromGitCommand implements Command, IEditorResponse, ILocks {
   private User user;
   private String url;
   private String username;
   private String password;

    @Override
    public JsonNode execute() {
        //TODO
        return generateResponse();
    }

    @Override
    public JsonNode generateResponse() {
        JsonNode response;
        try {
            acquireStructureReadLock();
            response = IEditorResponse.super.generateResponse();
            releaseStructureReadLock();
        } catch (Exception e) {
            releaseStructureReadLock();
            response = generateFailureResponse(e.getMessage());
        }
        return response;
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
