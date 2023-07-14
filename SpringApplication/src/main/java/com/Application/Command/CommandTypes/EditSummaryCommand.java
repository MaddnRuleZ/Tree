package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class EditSummaryCommand implements Command, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private String summary;

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

    public void setRoot(Root root) {
        this.root = root;
    }

    public Root getRoot() {
        return root;
    }

    public UUID getElement() {
        return element;
    }

    public String getSummary() {
        return summary;
    }
}
