package com.application.command.types;

import com.application.command.types.interfaces.IMoveElementCommand;
import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ProcessingException;
import com.application.exceptions.TypeException;
import com.application.tree.Element;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class MoveElementTreeCommand extends Command implements IMoveElementCommand {
    /**
     * user that holds information of LaTeX-Project
     */
    private Root root;
    /**
     * element to move
     */
    private UUID element;
    /**
     * new parent of the element
     */
    private UUID newParent;
    /**
     * the child after which the element should be added
     */
    private UUID previousElement;


    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Element newParent = root.searchForID(this.newParent);
            Element element = root.searchForID(this.element);

            if (newParent == null || element == null || previousElement == null) {
                throw new ElementNotFoundException();
            } else if (!(newParent instanceof  Parent)) {
                throw new TypeException(Parent.class.getSimpleName(), newParent.getClass().getSimpleName());
            } else {
                moveElement(element, (Parent) newParent, previousElement, Root.MIN_LEVEL);
                this.setSuccess(true);
            }
        } catch (IndexOutOfBoundsException | ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }
        return generateResponse(false);
    }


    public Root getRoot() {
        return root;
    }

    @JsonProperty
    public UUID getNewParent() {
        return newParent;
    }

    @JsonProperty
    public UUID getElement() {
        return element;
    }

    @JsonProperty
    public UUID getPreviousElement() {
        return previousElement;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    @JsonProperty
    public void setNewParent(UUID newParent) {
        this.newParent = newParent;
    }

    @JsonProperty
    public void setElement(UUID element) {
        this.element = element;
    }

    @JsonProperty
    public void setPreviousElement(UUID previousElement) {
        this.previousElement = previousElement;
    }
}