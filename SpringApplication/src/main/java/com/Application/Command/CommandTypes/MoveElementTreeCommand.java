package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Command.CommandTypes.Interfaces.ITreeResponse;
import com.Application.Tree.elements.sectioning.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class MoveElementTreeCommand implements Command, IMoveElementCommand, ITreeResponse, ILocks {
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
            response = ITreeResponse.super.generateResponse();
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

    public UUID getNewParent() {
        return newParent;
    }

    public UUID getElement() {
        return element;
    }

    public UUID getPreviousElement() {
        return previousElement;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
