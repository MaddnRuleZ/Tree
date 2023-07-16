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


    /**
     *
     *
     * @param element
     * @return
     */
    public void addChild(Element element) {
        this.childElements.add(element);
    }

    public void addChildOnIndex(int index, Element newChild) {
        this.childElements.add(index, newChild);
    }

    public boolean addChildAfter(Element newChild, Element previousChild) {
        return false;
    }

    public Element getPreviousElement(Element element) {
        return null;
    }

    public boolean removeChild(Element element) {
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
    @Override
    public String[] toText() {
        List<String>  arrayList = new ArrayList<>();
        String[] curr = this.getText();

        // unclean
        if (curr != null) {
            arrayList.add(this.getOptions());

            if (this.comment.getContent() != null) {
                arrayList.addAll(this.comment.getContent());
            }

            if (this.summary.getContent() != null) {
                arrayList.addAll(this.summary.getContent());
            }

            if (textBlock.getTextParts() != null) {
                for (List<String> list: textBlock.getTextParts()) {
                    if (list != null) {
                        arrayList.addAll(list);
                    }
                }
            }
        }

        for (Element element: childElements) {
            arrayList.addAll(Arrays.asList(element.toText()));
        }
        return arrayList.toArray(new String[0]);
    }
}