package com.application.command.types;

import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ProcessingException;
import com.application.tree.Element;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * command to edit the comment of an element
 */
public class EditCommentCommand extends Command {
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
        this.getLockManager().acquireStructureWriteLock();
        try {
            Element elementFound = this.getUser().getRoot().searchForID(this.element);
            if(elementFound == null) {
                throw new ElementNotFoundException("Element");
            } else {
                elementFound.setComment(this.comment);
                this.setSuccess(true);
            }
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            this.getLockManager().releaseStructureWriteLock();
        }

        return generateResponse(true);
    }


    @JsonProperty
    public UUID getElement() {
        return element;
    }
    @JsonProperty
    public String getComment() {
        return comment;
    }
    @JsonProperty
    public void setElement(UUID element) {
        this.element = element;
    }
    @JsonProperty
    public void setComment(String comment) {
        this.comment = comment;
    }
}
