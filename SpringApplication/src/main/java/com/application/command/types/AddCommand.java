package com.application.command.types;

import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.ParseException;
import com.application.exceptions.ProcessingException;
import com.application.exceptions.TypeException;
import com.application.interpreter.Parser;
import com.application.tree.Element;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;

/**
 * command to add a new element to the tree
 */
public class AddCommand extends Command {
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
        this.getLockManager().acquireStructureWriteLock();
        try {
            Element foundParentElement = this.getUser().getRoot().searchForID(this.parent);

            if(foundParentElement == null ) {
                throw new ElementNotFoundException();
            } else if (!(foundParentElement instanceof Parent)) {
                throw new TypeException(Parent.class.getSimpleName(), foundParentElement.getClass().getSimpleName());
            }

            Parent parentElement = (Parent) foundParentElement;
            Parser parser = new Parser(this.content);
            Roots foundRoot = parser.startParsing();

            if (foundRoot instanceof Root) {
                if (this.getUser().getRoot().getChildren().size() == 0) {
                    throw new ParseException(this.content);
                }

                List<Element> children = this.getUser().getRoot().getChildren();
                int index = parentElement.getIndexOfChild(this.previousChild);
                for (Element child : children) {
                    child.setParent(parentElement);
                    parentElement.addChildOnIndex(index, child);
                    index++;
                }
                this.setSuccess(true);
            } else {
                throw new ParseException(this.getClass().getSimpleName(), foundRoot.getClass().getSimpleName(), Root.class.getSimpleName());
            }
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            this.getLockManager().releaseStructureWriteLock();
        }
        return generateResponse(true);
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @JsonProperty
    public UUID getParent() {
        return parent;
    }

    @JsonProperty
    public UUID getPreviousChild() {
        return previousChild;
    }

    @JsonProperty
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty
    public void setParent(UUID parent) {
        this.parent = parent;
    }

    @JsonProperty
    public void setPreviousChild(UUID previousChild) {
        this.previousChild = previousChild;
    }
}
