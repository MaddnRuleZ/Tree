package com.Application.Tree.elements.parent;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.childs.BlockElement;

import java.util.Map;
import java.util.UUID;

/**
 * Environment Class
 *
 * The Environment class represents a parent container that holds elements within a program.
 *
 * An Environment typically groups and organizes various child elements within a specific context.
 * It is defined by its startPart and endPart strings, which are used to identify the beginning and
 * end of the environment container within the program. The hierarchical level (level) represents the
 * nesting depth of this environment in relation to other elements.
 */
public class Environment extends Parent {
    public static String DEFAULT_OPENING = "\\begin";
    public static String DEFAULT_ENDING = "\\end";
    public static int DEFAULT_LEVEL = 9;

    /**
     * Constructor for creating a new Environment object with the specified startPart, endPart, and level.
     *
     * @param startPart The startPart string that identifies the beginning of the environment container.
     * @param endPart   The endPart string that identifies the end of the environment container.
     * @param level     The hierarchical level of the environment container.
     */
    public Environment(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
    }

    /**
     * Add a new TextBlock to the Environment.
     * If there are already child elements in the Environment, the new TextBlock will be added at the same level.
     * Otherwise, it will be added as a child of the Environment.
     *
     * @param line The line to scan for Summary Comment or NewLine to be added to the TextBlock.
     * @return The newly created or existing TextBlockElement where the line is added.
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (this.childElements.size() == 0) {
            BlockElement block = generateTextBlockAsChild();
            block.addTextBlockToElem(line);
            return block;
        } else {
            BlockElement block = generateTextSameLevel();
            block.addTextBlockToElem(line);
            return block;
        }
    }

    @Override
    public Element searchForID(UUID id) {
        if (this.getId().equals(id)) {
            return this;
        } else {
            for (Element child: this.getChildElements()) {
                Element foundElement = child.searchForID(id);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }
        return null;
    }

    @Override
    //TODO environment end und start part
    public void toLaTeX(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        super.toLaTeXStart(map, key, level);
        String indentation = getIndentation(level);

        StringBuilder text = map.get(key);

        text.append(indentation).append(this.getStartPart());
        if(this.options != null) {
            text.append("[").append(this.options).append("]");
        }
        text.append("\n");
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key, level);
            }
        }
        text.append(indentation).append(this.getEndPart());
        text.append("\n");

        super.toLaTeXEnd(map, key, level);
    }

}
