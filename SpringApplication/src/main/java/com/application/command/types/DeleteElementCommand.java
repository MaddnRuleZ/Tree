package com.application.command.types;

import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ProcessingException;
import com.application.tree.Element;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;

/**
 * command to delete an element from the tree
 */
public class DeleteElementCommand extends Command {
    /**
     * The element to be deleted
     */
    private UUID element;
    /**
     * indicates if children should be deleted
     * default false
     */
    private boolean cascading = false;

    @Override
    public JsonNode execute() {
        this.getLockManager().acquireStructureWriteLock();
        try {
            Element elementFound = this.getUser().getRoot().searchForID(this.element);
            if(elementFound == null) {
                throw new ElementNotFoundException();
            } else {
                delete(elementFound, this.getUser().getRoot());
            }
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            this.getLockManager().releaseStructureWriteLock();
        }
        return generateResponse(true);
    }



    /**
     * Deletes an element from the tree
     * @param element the element to be deleted
     */
    public void delete(Element element, Root root) {
        Parent parent = element.getParentElement();

        if(!this.isCascading() && element instanceof Parent) {
            int index;
            if(parent == null) {
                index = root.getChildren().indexOf(element);
            } else {
                index = parent.getChildren().indexOf(element);
            }
            // cascading only false if element is of type parent
            List<Element> children = ((Parent) element).getChildren();
            for(Element child : children) {
                child.setParent(parent);
                if(parent == null) {
                    root.addChildOnIndex(index, child);
                } else {
                    parent.addChildOnIndex(index, child);
                }
                index++;
            }
        }
        if(parent == null) {
            root.removeChild(element);
        } else {
            parent.removeChild(element);
        }

        this.setSuccess(true);

    }
    @JsonProperty
    public UUID getElement() {
        return element;
    }

    @JsonProperty
    public boolean isCascading() {
        return cascading;
    }

    @JsonProperty
    public void setCascading(boolean cascading) {
        this.cascading = cascading;
    }

    @JsonProperty
    public void setElement(UUID element) {
        this.element = element;
    }
}
