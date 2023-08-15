package com.application.tree.elements.parent;

import com.application.exceptions.ElementNotFoundException;
import com.application.tree.Element;
import com.application.tree.elements.childs.BlockElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Element Composite's Parent Abstract Class
 *
 * The Parent abstract class represents a container element that groups and organizes child elements within the Parser.
 *
 * A Parent element is defined by its startPart and endPart strings, which are used to identify the beginning and
 * end of the parent container within the program. The hierarchical level (level) represents the nesting depth of this
 * parent element in relation to other elements.
 */
public abstract class Parent extends Element {
    protected final List<Element> children;
    /**
     * Constructor for creating a new Parent object with the specified startPart, endPart, and level.
     *
     * @param startPart The startPart string that identifies the beginning of the parent container.
     * @param endPart   The endPart string that identifies the end of the parent container.
     * @param level     The hierarchical level of the parent container.
     */
    public Parent(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
        children = new ArrayList<>();
        this.type = this.getClass().getSimpleName();
    }

    /**
     * Add an TextBlock to the Element,
     * if the Parent does already have an TextBlock, add the Text to the Element
     *
     * @param line current Line in the Text
     * @return the TextBlock if added or this
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (summary.extractContent(line)) {
            return this;
        }

        if (summary.getSummary().size() == 0 && getComment().extractContent(line)) {
            return this;
        }

        BlockElement block = generateTextBlockAsChild();
        block.addTextBlockToElem(line);
        return block;
    }

    /**
     * Add a new TextBlock as Child of the currentElement and add first Line
     *
     * @return TextBlockElement just created
     */
    protected BlockElement generateTextBlockAsChild() {
        BlockElement textBlockElement = new BlockElement();
        this.addChild(textBlockElement);
        textBlockElement.setParent(this);
        return textBlockElement;
    }


    public void addChild(Element element) {
        this.children.add(element);
    }

    public void addChildOnIndex(int index, Element newChild) {
        this.children.add(index, newChild);
    }

    public void addChildAfter(Element previousElement, Element newChild) throws ElementNotFoundException {
        int index = this.children.indexOf(previousElement);
        if (index == -1) {
            throw new ElementNotFoundException();
        }
        this.children.add(index + 1, newChild);
    }

    public boolean removeChild(Element element) {
        return this.children.remove(element);
    }

    public List<Element> getChildren() {
        return this.children;
    }

    @Override
    public Element searchForID(UUID id) {
        if (this.getId().equals(id)) {
            return this;
        } else {
            for (Element child: this.getChildren()) {
                Element foundElement = child.searchForID(id);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }
        return null;
    }

    @Override
    public int levelOfDeepestSectioningChild() {
        int deepestChildLevel = 0;

        for (Element child : this.children) {
            int childLevel = child.levelOfDeepestSectioningChild();
            deepestChildLevel = Math.max(deepestChildLevel, childLevel);
        }

        return deepestChildLevel;
    }

    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectNode node = super.toJsonEditor();
        if (this.children != null && !this.children.isEmpty()) {
            ArrayNode childrenNode = JsonNodeFactory.instance.arrayNode();
            for (Element child : this.children) {
                childrenNode.add(child.toJsonEditor());
            }
            node.set("children", childrenNode);
        }
        return node;
    }

    @Override
    public JsonNode toJsonTree() throws NullPointerException {
        ArrayNode node = (ArrayNode) super.toJsonTree();
        if (this.children != null && !this.children.isEmpty()) {
            for (Element child : this.children) {
                node.addAll((ArrayNode) child.toJsonTree());
            }
        }
        return node;
    }

    /**
     * Returns the index of the child with the specified id
     * if no child with the specified id exists or the id is null, -1 is returned
     * @param id
     * @return index of child with specified id
     */
    public int getIndexOfChild(UUID id) {
        if(id == null) {
            return -1;
        }
        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}