package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.sectioning.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class EditContentCommand implements Command, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private String content;

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

    public Root getRoot() {
        return root;
    }

    public UUID getElement() {
        return element;
    }

    public String getContent() {
        return content;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
