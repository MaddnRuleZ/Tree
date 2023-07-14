package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class LoadFromFolderCommand implements Command, IEditorResponse, ILocks {
    private User user;
    private String path;

    @Override
    public JsonNode execute(boolean success) {
        //TODO
        return generateResponse(false, null);
    }

    @Override
    public JsonNode generateResponse(boolean success, String message) {
        JsonNode response;
        if (success) {
            try {
                acquireStructureReadLock();
                response = IEditorResponse.super.generateResponse();
            } catch (JsonProcessingException e) {
                response = generateFailureResponse(e.getMessage());
                success = false;
            } finally {
                releaseStructureReadLock();
            }
        } else {
            response = generateFailureResponse(message);
        }
        return response;
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
