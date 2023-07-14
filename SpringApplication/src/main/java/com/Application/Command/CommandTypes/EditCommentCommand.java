package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Tree.Element;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class EditCommentCommand extends Command implements IEditorResponse {
    private Root root;
    private UUID element;
    private String comment;


    //TODO Fehlerbehandlung: im Moment wird auch bei Nichtausführbarkeit der Baum zurückgegeben
    //Comment muss noch geparst werden: in setComment???
    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Element elementFound = root.searchForID(this.element, 0);
            if(elementFound == null) {
                releaseStructureWriteLock();
                this.setSuccess(false);
            } else {
                elementFound.setComment(comment);
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
    public String getComment() {
        return comment;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
