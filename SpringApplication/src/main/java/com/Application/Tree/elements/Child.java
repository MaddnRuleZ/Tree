package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.List;
import java.util.UUID;

/**
 *
 */
public class Child extends Element {

    /**
     *
     *
     * @param startPart
     * @param endPart
     * @param startIndex
     */
    public Child(String startPart, String endPart, int startIndex, int level) {
        super(startPart, endPart, startIndex, level);
    }

    @Override
    public List<String> toText() {
        return this.text;
    }

    @Override
    public Child searchForID(UUID id) {
        if (this.getId().equals(id)) {
            return this;
        }
        return null;
    }

    @Override
    public int levelOfDeepestSectioningChild() {
        return 0;
    }
}
