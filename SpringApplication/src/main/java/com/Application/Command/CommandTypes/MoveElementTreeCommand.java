package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Tree.Element;
import com.Application.Tree.elements.parent.Parent;
import com.Application.Tree.elements.roots.Root;
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
            Parent newParent = (Parent) root.searchForID(this.newParent);
            Element element = root.searchForID(this.element);
            Element previousElement = newParent.searchForID(this.previousElement);

            if (newParent == null || element == null || previousElement == null) {
                this.setSuccess(false);
                this.setFailureMessage("Element, new parent or previous element not found");
            } else {
                moveElement(element, newParent, previousElement, root.MIN_LEVEL);
                this.setSuccess(true);
            }
        } catch (Exception e) {
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

    public UUID getNewParent() {
        return newParent;
    }

    public UUID getElement() {
        return element;
    }

    public UUID getPreviousElement() {
        return previousElement;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
