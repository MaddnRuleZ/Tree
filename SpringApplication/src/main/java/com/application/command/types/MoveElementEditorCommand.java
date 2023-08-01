package com.application.command.types;

import com.application.command.types.interfaces.IMoveElementCommand;
import com.application.exceptions.*;
import com.application.tree.Element;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * command to move an element
 */
public class MoveElementEditorCommand extends Command implements IMoveElementCommand {
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
            Element newParent = this.getRoot().searchForID(this.newParent);
            Element element = this.getRoot().searchForID(this.element);

            if (newParent == null || element == null) {
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
        return generateResponse(true);
    }

    @JsonProperty
    public UUID getElement() {
        return element;
    }

    @JsonProperty
    public UUID getNewParent() {
        return newParent;
    }

    @JsonProperty
    public UUID getPreviousElement() {
        return previousElement;
    }

    @JsonProperty
    public void setElement(UUID element) {
        this.element = element;
    }

    @JsonProperty
    public void setNewParent(UUID newParent) {
        this.newParent = newParent;
    }

    @JsonProperty
    public void setPreviousElement(UUID previousElement) {
        this.previousElement = previousElement;
    }

}
