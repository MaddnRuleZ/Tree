package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.sectioning.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class DeleteElementCommand implements Command, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private boolean cascading;

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

    public UUID getElement() {
        return element;
    }

    public boolean isCascading() {
        return cascading;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
