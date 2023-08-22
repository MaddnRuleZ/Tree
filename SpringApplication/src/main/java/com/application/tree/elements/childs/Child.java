package com.application.tree.elements.childs;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;

import java.util.Map;
import java.util.UUID;

/**
 * Element -Child Class for Elements like Labels
 *
 * Child's can't have Children themselves and therefor represent a Leaf in the Composite Tree
 */
public class Child extends Element {

    /**
     * Create a new Child,
     * call Element constructor and set general Values
     *
     * @param startPart Start String to init a Child Element
     * @param endPart String necessary to end the Child Element
     * @param level nesting level of the Element
     */
    public Child(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
        this.type = this.getClass().getSimpleName();
    }

    /**
     * add a TextBlock on the same Level
     *
     * @return the new added TextBlock
     */
    @Override
    public Element addTextBlockToElem(String line) {
        return generateTextBlockSameLevel();
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
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXStart(map, key, level, exportComment, exportSummary);
        String indentation = getIndentation(level);
        StringBuilder text = map.get(key);

        // \startPart{content}, e.g \label{content}
        text.append(indentation).append(this.getStartPart()).append("{").append(this.content).append("}");
        text.append("\n");

        super.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }
}
