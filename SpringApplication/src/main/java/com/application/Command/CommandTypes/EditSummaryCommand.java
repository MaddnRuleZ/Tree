package com.application.Command.CommandTypes;

import com.application.Exceptions.ElementNotFoundException;
import com.application.Exceptions.ProcessingException;
import com.application.Tree.Element;
import com.application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * Command to edit the summary of an element
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

    public UUID getElement() {
        return element;
    }

    public String getSummary() {
        return summary;
    }
}
