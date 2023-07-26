package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Exceptions.ElementNotFoundException;
import com.Application.Exceptions.ProcessingException;
import com.Application.Exceptions.TypeException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.parent.Parent;
import com.Application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * Command to move an element
 */
public class MoveElementEditorCommand extends Command implements IMoveElementCommand {
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


            if (newParent == null || element == null) {
                throw new ElementNotFoundException();
            } else if (!(newParent instanceof  Parent)) {
                throw new TypeException(Parent.class.getSimpleName(), newParent.getClass().getSimpleName());
            } else {
                moveElement(element, (Parent) newParent, previousElement, Root.MIN_LEVEL);
                this.setSuccess(true);
            }
        } catch (ProcessingException | IndexOutOfBoundsException e) {
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
    public UUID getNewParent() {
        return newParent;
    }

    public UUID getPreviousElement() {
        return previousElement;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
