package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class AddCommand implements Command, IEditorResponse, ILocks {

    private Root root;
    private String content;
    private UUID parent;
    private UUID previousChild;


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



    public String getContent() {
        return content;
    }

    public UUID getParent() {
        return parent;
    }
    public UUID getPreviousChild() {
        return previousChild;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }


}
