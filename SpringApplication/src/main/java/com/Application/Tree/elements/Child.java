package main.java.com.Application.Tree.elements;

import Tree.Element;

/**
 *
 */
public class Child extends Element {

    /**
     *
     *
     *
     * @param startPart
     * @param endPart
     * @param startIndex
     */
    public Child(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex);
    }

    /**
     *
     * @param child element to add
     */
    @Override
    public void addChild(Element child) {
        // fix?
    }
}
