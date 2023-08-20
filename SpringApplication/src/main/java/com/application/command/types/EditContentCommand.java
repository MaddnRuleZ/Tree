package com.application.command.types;

import com.application.command.types.interfaces.IEditCommand;
import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ProcessingException;
import com.application.tree.Element;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * command to edit the content of an element
 */
public class EditContentCommand extends Command implements IEditCommand {
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
        this.getLockManager().acquireStructureWriteLock();
        try {
            if(this.content.equals("")) {
                throw new ProcessingException("Content cannot be empty");
            }
            checkBadString(this.content);
            Element elementFound = this.getUser().getRoot().searchForID(this.element);
            if(elementFound == null) {
                throw new ElementNotFoundException("Element");
            } else {
                elementFound.setContentManually(content);
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
    public String getContent() {
        return content;
    }

    @JsonProperty
    public void setElement(UUID element) {
        this.element = element;
    }

    @JsonProperty
    public void setContent(String content) {
        this.content = content;
    }
}
