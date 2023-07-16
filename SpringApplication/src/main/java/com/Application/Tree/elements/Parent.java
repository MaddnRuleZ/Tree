package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public abstract class Parent extends Element {
    protected final List<Element> childElements;

    /**
     *
     *
     * @param startPart
     * @param endPart
     * @param startIndex
     * @param level
     */
    public Parent(String startPart, String endPart, int startIndex, int level) {
        super(startPart, endPart, startIndex, level);
        childElements = new ArrayList<>();
    }

    public void addChild(Element element) {
        this.childElements.add(element);
    }

    public void addChildOnIndex(int index, Element newChild) {
        this.childElements.add(index, newChild);
    }

    public boolean removeChild(Element element) {
        // todo
        return false;
    }

    public List<Element> getChildElements() {
        return this.childElements;
    }

    /**
     * recursiv generation of the Latex code
     * generate the LaTeX code from the current Element as well as all Children, add it together and return it
     *
     * @return LateX code of the current Element
     */
    public List<String> toText() {
        List<String> outText = new ArrayList<>();
        outText.add(options);

        for (Element child: this.childElements) {
            outText.addAll(child.toText());
        }
        return outText;
    }

}