package com.Application.Command.CommandTypes;

import com.Application.Exceptions.ElementNotFoundException;
import com.Application.Exceptions.ProcessingException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * Command to edit the content of an element
 */
public class EditContentCommand extends Command {
    /**
     * The root of the tree
     */
    private Root root;
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
            Element elementFound = root.searchForID(this.element);
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



    public Root getRoot() {
        return root;
    }

    public UUID getElement() {
        return element;
    }

    public String getContent() {
        return content;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
