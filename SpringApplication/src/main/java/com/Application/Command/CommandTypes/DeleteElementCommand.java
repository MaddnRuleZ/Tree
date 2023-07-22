package com.Application.Command.CommandTypes;

import com.Application.Exceptions.ElementNotFoundException;
import com.Application.Exceptions.ProcessingException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.parent.Parent;
import com.Application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;

/**
 * Command to delete an element from the tree
 */
public class DeleteElementCommand extends Command {
    /**
     * The root of the tree
     */
    private Root root;
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
        try {
            acquireStructureWriteLock();
            Element elementFound = root.searchForID(this.element);
            if(elementFound == null) {
                throw new ElementNotFoundException();
            } else {
                delete(elementFound);
            }
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }
        return generateResponse(true);
    }



    /**
     * Deletes an element from the tree
     * @param element the element to be deleted
     */
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
        if(parent.removeChild(element)) {
            this.setSuccess(true);
        } else {
            this.setFailureMessage("Beim Löschen des Elements ist ein Fehler aufgetreten");
            this.setSuccess(false);
        }
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

    //--------TODO: nur für Testzwecke --------------

    public void setElement(UUID element) {
        this.element = element;
    }

    public void setCascading(boolean cascading) {
        this.cascading = cascading;
    }
    //--------TODO: nur für Testzwecke --------------
}
