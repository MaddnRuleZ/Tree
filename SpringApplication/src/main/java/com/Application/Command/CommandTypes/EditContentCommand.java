package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Tree.Element;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * Command to edit the content of an element
 */
public class EditContentCommand extends Command implements IEditorResponse {
    /**
     * The root of the tree
     */
    private Root root;
    /**
     * The element to be edited
     */
    private UUID element;
    /**
     * The new content of the element
     */
    private String content;

    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Element elementFound = root.searchForID(this.element);
            if(elementFound == null) {
                releaseStructureWriteLock();
                this.setSuccess(false);
            } else {
                elementFound.setContent(content);
                this.setSuccess(true);
            }
        } catch (Exception e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }

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

    public String getContent() {
        return content;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
