package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.Element;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class EditCommentCommand implements Command, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private String comment;


    //TODO Fehlerbehandlung: im Moment wird auch bei Nichtausführbarkeit der Baum zurückgegeben
    //Comment muss noch geparst werden: in setComment???
    @Override
    public JsonNode execute() {
        JsonNode response = null;
        try {
            acquireStructureWriteLock();
            Element elementFound = root.searchForID(this.element);
            if(elementFound == null) {
                releaseStructureWriteLock();
            } else {
                elementFound.setComment(comment);
                releaseStructureWriteLock();
                response = generateResponse();

            }
        } catch (Exception e) {
            releaseStructureWriteLock();
        }

        return response;
    }

    @Override
    public JsonNode generateResponse() {
        JsonNode response;

        try {
            acquireStructureReadLock();
            response = IEditorResponse.super.generateResponse();
            releaseStructureReadLock();
        } catch (JsonProcessingException e) {
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
    public String getComment() {
        return comment;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
