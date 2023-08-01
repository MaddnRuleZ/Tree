package com.application.command.types;

import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ProcessingException;
import com.application.tree.Element;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * command to edit the content of an element
 */
public class EditContentCommand extends Command {
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
            Element elementFound = this.getRoot().searchForID(this.element);
            if(elementFound == null) {
                throw new ElementNotFoundException();
            } else {
                elementFound.setContentManually(content);
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
