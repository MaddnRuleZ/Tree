package com.application.command.types;

import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ProcessingException;
import com.application.tree.Element;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * command to edit the summary of an element
 */
public class EditSummaryCommand extends Command {
    /**
     * The root of the tree
     */
    private Root root;
    /**
     * The element to be edited
     */
    private UUID element;
    /**
     * The new summary of the element
     */
    private String summary;

    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Element elementFound = root.searchForID(this.element);
            if(elementFound == null) {
                throw new ElementNotFoundException();
            } else {
                elementFound.setSummary(this.summary);
                elementFound.setChooseManualSummary(true);
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


    public void setRoot(Root root) {
        this.root = root;
    }

    public Root getRoot() {
        return root;
    }

    @JsonProperty
    public UUID getElement() {
        return element;
    }

    @JsonProperty
    public String getSummary() {
        return summary;
    }

    @JsonProperty
    public void setElement(UUID element) {
        this.element = element;
    }

    @JsonProperty
    public void setSummary(String summary) {
        this.summary = summary;
    }
}