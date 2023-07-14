package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Tree.Element;
import com.Application.Tree.elements.Parent;
import com.Application.Tree.elements.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;

public class DeleteElementCommand extends Command implements IEditorResponse {
    private Root root;
    private UUID element;
    private boolean cascading;

    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Element elementFound = root.searchForID(this.element, 0);
            if(elementFound == null) {
                releaseStructureWriteLock();
                this.setSuccess(false);
            } else {
                delete(elementFound);
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

    public void delete(Element element) {
        Parent parent = element.getParentElement();
        if(!this.cascading) {
            int index = parent.getChildElements().indexOf(element);
            // cascading only false if element is of type parent
            List<Element> children = ((Parent) element).getChildElements();
            for(Element child : children) {
                child.setParent(parent);
                parent.addChildOnIndex(index, child);
                index++;
            }
        }
        parent.removeChild(element);
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
