package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.ArrayList;
import java.util.List;
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
     */
    public Child(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
    }

    // og just text.add(line)
    @Override
    public Element addText(String line) {
        BlockElement textBlockElement = new BlockElement(null, null);
        Parent parent = (Parent) this.parentElement;
        parent.addChild(textBlockElement);
        text.add(line);
        return textBlockElement;
    }

    @Override
    public List<String> toText() {
        List<String> text = new ArrayList<>();
        text.addAll(this.text);
        return text;
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
