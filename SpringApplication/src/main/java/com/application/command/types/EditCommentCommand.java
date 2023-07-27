package com.application.command.types;

import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ProcessingException;
import com.application.tree.Element;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * command to edit the comment of an element
 */
public class EditCommentCommand extends Command {
    /**
     * The root of the tree
     */
    private Root root;
    /**
     * The element to be edited
     */
    private UUID element;
    /**
     * The new comment of the element
     */
    private String comment;


    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Element elementFound = root.searchForID(this.element);
            if(elementFound == null) {
                throw new ElementNotFoundException();
            } else {
                elementFound.setComment(comment);
                this.setSuccess(true);
            }
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }

        return generateResponse(true);
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
