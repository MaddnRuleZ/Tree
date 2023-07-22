package com.Application.Tree.elements.childs;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;

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
        return generateTextSameLevel();
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

        text.append(this.getStartPart());
        text.append("{").append(this.content).append("}");

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key);
        }
    }
}
