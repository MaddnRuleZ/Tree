package com.application.Command.CommandTypes;

import com.application.Exceptions.ElementNotFoundException;
import com.application.Exceptions.ParseException;
import com.application.Exceptions.ProcessingException;
import com.application.Exceptions.TypeException;
import com.application.Interpreter.Parser;
import com.application.Tree.Element;
import com.application.Tree.elements.parent.Parent;
import com.application.Tree.elements.roots.Root;
import com.application.Tree.elements.roots.Roots;
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
            Element foundParentElement = root.searchForID(this.parent);

            if(foundParentElement == null ) {
                throw new ElementNotFoundException();
            } else if (!(foundParentElement instanceof Parent)) {
                throw new TypeException(Parent.class.getSimpleName(), foundParentElement.getClass().getSimpleName());
            }

            Parent parentElement = (Parent) foundParentElement;
            Parser parser = new Parser(this.content);
            Roots foundRoot = parser.startParsing();

            if (foundRoot instanceof Root) {
                if (root.getChildren().size() == 0) {
                    throw new ParseException("Es konnte kein Element geparst werden.");
                }

                List<Element> children = root.getChildren();
                int index = parentElement.getIndexOfChild(this.previousChild);
                for (Element child : children) {
                    child.setParent(parentElement);
                    parentElement.addChildOnIndex(index, child);
                    index++;
                }
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

    public String getContent() {
        return content;
    }

    public UUID getParent() {
        return parent;
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
