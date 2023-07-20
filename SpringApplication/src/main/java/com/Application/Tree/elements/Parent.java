package com.Application.Tree.elements;

import com.Application.Tree.Element;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public abstract class Parent extends Element {
    protected final List<Element> childElements;

    /**
     *
     * @param startPart
     * @param endPart
     * @param level
     */
    public Parent(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
        childElements = new ArrayList<>();
    }

    @Override
    public Element addTextBlockToElem(String line) {
        if (childElements.size() == 0) {
            return generateTextBlockAsChild(line);
            /*
            BlockElement textBlockElement = new BlockElement(null, null);
            this.addChild(textBlockElement);
            textBlockElement.setParent(this);
            textBlockElement.addTextBlockToElem(line);
            return textBlockElement;
            */

        } else {
            text.add(line);
            return this;
        }
    }

    // move n-1
    protected BlockElement generateTextBlockAsChild(String line) {
        BlockElement textBlockElement = new BlockElement(null, null);
        this.addChild(textBlockElement);
        textBlockElement.setParent(this);
        textBlockElement.addTextBlockToElem(line);
        return textBlockElement;
    }

    protected BlockElement generateTextToParent(String line) {
        BlockElement textBlockElement = new BlockElement(null, null);
        textBlockElement.setParent(parentElement);
        parentElement.addChild(textBlockElement);
        textBlockElement.addTextBlockToElem(line);
        return textBlockElement;
    }

    public void addChild(Element element) {
        this.childElements.add(element);
    }

    public void addChildOnIndex(int index, Element newChild) {
        this.childElements.add(index, newChild);
    }

    public void addChildAfter(Element previousElement, Element newChild) throws IndexOutOfBoundsException, NullPointerException {
        int index = this.childElements.indexOf(previousElement);
        if(newChild == null) throw new NullPointerException("newChild is null");
        this.childElements.add(index + 1, newChild);
    }

    public boolean removeChild(Element element) {
        return this.childElements.remove(element);
    }

    public List<Element> getChildElements() {
        return this.childElements;
    }



    @Override
    public int levelOfDeepestSectioningChild() {
        int deepestChildLevel = 0;

        for (Element child : this.childElements) {
            int childLevel = child.levelOfDeepestSectioningChild();
            deepestChildLevel = Math.max(deepestChildLevel, childLevel);
        }

        return deepestChildLevel;
    }

    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectNode node = super.toJsonEditor();
        if (this.childElements != null && !this.childElements.isEmpty()) {
            ArrayNode childrenNode = JsonNodeFactory.instance.arrayNode();
            for (Element child : this.childElements) {
                childrenNode.add(child.toJsonEditor());
            }
            node.set("children", childrenNode);
        }
        return node;
    }

    @Override
    public ArrayNode toJsonTree() throws NullPointerException {
        ArrayNode node = super.toJsonTree();
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                node.addAll(child.toJsonTree());
            }
        }
        return node;
    }
}