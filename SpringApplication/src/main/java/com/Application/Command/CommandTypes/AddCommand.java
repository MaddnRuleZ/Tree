package com.Application.Command.CommandTypes;

import com.Application.Exceptions.ElementNotFoundException;
import com.Application.Exceptions.ParseException;
import com.Application.Exceptions.ProcessingException;
import com.Application.Exceptions.TypeException;
import com.Application.Interpreter.Parser;
import com.Application.Tree.Element;
import com.Application.Tree.elements.parent.Parent;
import com.Application.Tree.elements.roots.Root;
import com.Application.Tree.elements.roots.Roots;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;

/**
 * Command to add a new element to the tree
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
            Element parentElement = root.searchForID(this.parent);
            Element previousChild = root.searchForID(this.previousChild);
            if(parentElement == null || previousChild == null) {
                throw new ElementNotFoundException();
            } else if (!(parentElement instanceof Parent)) {
                throw new TypeException(Parent.class.getSimpleName(), parentElement.getClass().getSimpleName());
            } else {
                Parser parser = new Parser(this.content);
                Roots root = parser.startParsing();

                if (root instanceof Root) {
                    if (((Root) root).getChildren().size() == 0) {
                        throw new ParseException("Es konnte kein Element geparst werden.");
                    }

                    List<Element> children = ((Parent) root).getChildElements();
                    int index = ((Parent) parentElement).getChildElements().indexOf(previousChild) + 1;
                    for (Element child : children) {
                        child.setParent((Parent) parentElement);
                        ((Parent) parentElement).addChildOnIndex(index, child);
                        index++;
                    }
                    this.setSuccess(true);
                }
            }
        } catch (ProcessingException e) {
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
