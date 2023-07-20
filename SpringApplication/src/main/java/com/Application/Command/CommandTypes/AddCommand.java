package com.Application.Command.CommandTypes;

import com.Application.Tree.Element;
import com.Application.Tree.elements.parent.Parent;
import com.Application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

/**
 * Command to add an element to the tree
 */
public class AddCommand extends Command {
    /**
     * The root of the tree
     */
    private Root root;
    /**
     * The content of the element to be added
     */
    private String content;
    /**
     * The parent of the element to be added
     */
    private UUID parent;
    /**
     * the child after which the element should be added
     */
    private UUID previousChild;


    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Parent parentElement = (Parent) root.searchForID(this.parent);
            Element previousChild = root.searchForID(this.previousChild);
            if(parentElement == null) {
                this.setFailureMessage("Parent or previous child not found");
                this.setSuccess(false);
            } else {
                //TODO parse incoming content
                Element newElement = null;
                parentElement.addChildAfter(newElement, previousChild);
                this.setSuccess(true);
            }
        } catch (java.lang.ClassCastException e) {
            this.setFailureMessage("Parent is not a parent");
            this.setSuccess(false);
        } catch (Exception e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }
        return generateResponse(true);
    }


    public String getContent() {
        return content;
    }

    public UUID getParent() {
        return parent;
    }
    public UUID getPreviousChild() {
        return previousChild;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParent(UUID parent) {
        this.parent = parent;
    }

    public void setPreviousChild(UUID previousChild) {
        this.previousChild = previousChild;
    }
}
