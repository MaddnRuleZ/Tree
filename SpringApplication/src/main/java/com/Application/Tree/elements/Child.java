package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.UUID;

/**
 *
 *
 */
public class Child extends Element {

    /**
     *
     * @param startPart
     * @param endPart
     * @param startIndex
     */
    public Child(String startPart, String endPart, int startIndex, int level) {
        super(startPart, endPart, startIndex, level);
    }

    @Override
    public boolean validateIndicTextGeneration() {
        return true;
    }

    /**
     * @param child element to add
     */
    @Override
    public boolean addChild(Element child) {
        return false;
    }

    @Override
    public String[] toText() {
        if (text != null) {
            return text;
        }
        return null;
    }

    @Override
    public Child searchForID(UUID id) {
        if (this.getId().equals(id)) {
            return this;
        }
        return null;
    }
}
