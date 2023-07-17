package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * Command to move an element
 */
public class MoveElementEditorCommand extends Command implements IMoveElementCommand, IEditorResponse {
    /**
     * user that holds information of LaTeX-Project
     */
    private Root root;
    /**
     * element to move
     */
    private UUID element;
    /**
     * new parent of the element
     */
    private UUID newParent;
    /**
     * the child after which the element should be added
     */
    private UUID previousElement;

    @Override
    public JsonNode execute() {
        //TODO
        return generateResponse();
    }

    @Override
    public JsonNode generateResponse() {
        JsonNode response;
        if (this.isSuccess()) {
            try {
                acquireStructureReadLock();
                response = IEditorResponse.super.generateResponse();
            } catch (JsonProcessingException e) {
                response = generateFailureResponse(e.getMessage());
                this.setSuccess(false);
            } finally {
                releaseStructureReadLock();
            }
        } else {
            response = generateFailureResponse(this.getFailureMessage());
        }
        return response;
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
