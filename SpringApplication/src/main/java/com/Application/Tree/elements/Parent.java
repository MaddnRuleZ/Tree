package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class Parent extends Element {
    protected final List<Element> childElements;

    /**
     *
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

    /**
     * recursiv generation of the Latex code
     * generate the LaTeX code from the current Element as well as all Children, add it together and return it
     *
     * @return LateX code of the current Element
     */
    public List<String> toText() {
        List<String> outText = new ArrayList<>();
        for (Element child: this.childElements) {
            outText.addAll(child.toText());
        }
        return outText;
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
}