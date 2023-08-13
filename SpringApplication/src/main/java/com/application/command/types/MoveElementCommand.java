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

/**
 * command to move an element
 */
public class MoveElementCommand extends Command implements IMoveElementCommand {
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
    /**
     * if response should be generated for editor
     */
    private boolean isEditor;

    @Override
    public JsonNode execute() {
        this.getLockManager().acquireStructureWriteLock();
        try {
            Element newParent = this.getUser().getRoot().searchForID(this.newParent);
            Element element = this.getUser().getRoot().searchForID(this.element);

            if (newParent == null || element == null) {
                throw new ElementNotFoundException();
            } else if (!(newParent instanceof  Parent)) {
                throw new TypeException(Parent.class.getSimpleName(), newParent.getClass().getSimpleName());
            } else {
                moveElement(element, (Parent) newParent, previousElement, Root.getInstance().getMinLevel());
                this.setSuccess(true);
            }

        } catch (IndexOutOfBoundsException | ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            this.getLockManager().releaseStructureWriteLock();
        }
        return generateResponse(isEditor);
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


    public void setEditor(boolean editor) {
        isEditor = editor;
    }

}
