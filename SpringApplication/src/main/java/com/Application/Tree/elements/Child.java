package com.Application.Tree.elements;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


    @Override
    public Element addTextBlockToElem(String line) {
        BlockElement textBlockElement = new BlockElement(null, null);
        Parent parent = (Parent) this.parentElement;
        parent.addChild(textBlockElement);
        textBlockElement.setParent(parent);
        text.add(line);
        return textBlockElement;
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


    //TODO Child abstract method??? -> No implementation
    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n");

        if(this.summary != null) {
            this.summary.toLaTeX(map, key);
        }
        if(this.comment != null) {
            this.comment.toLaTeX(map, key);
        }

        text.append(this.content);

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key);
        }
    }
}
