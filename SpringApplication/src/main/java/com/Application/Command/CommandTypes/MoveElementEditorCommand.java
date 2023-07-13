package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class MoveElementEditorCommand implements Command, IMoveElementCommand, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private UUID newParent;
    private UUID previousElement;

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

    @Override
    public boolean moveElement() {
        return IMoveElementCommand.super.moveElement();
    }

    public Root getRoot() {
        return root;
    }

    public UUID getElement() {
        return element;
    }

    public UUID getNewParent() {
        return newParent;
    }

    public UUID getPreviousElement() {
        return previousElement;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
