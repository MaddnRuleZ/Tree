package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
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

    @Override
    public String[] toText() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(this.options);
        return arrayList.toArray(new String[0]);
    }

    @Override
    public Child searchForID(UUID id) {
        if (this.getId().equals(id)) {
            return this;
        }
        return null;
    }
}
