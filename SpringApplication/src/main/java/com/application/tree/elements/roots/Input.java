package com.application.tree.elements.roots;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.elements.childs.BlockElement;
import com.application.tree.elements.parent.Parent;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class Represents an Input Statement in the LateX file and is also an Element
 *
 * Represents an Input Document inside the main.tex file and Stores all Items of the Document inside it as its Children
 */
public class Input extends Parent implements Roots {
    private static final String START_PART = "\\\\input";

    /**
     * Input Constructor, call Constructor of Parent
     */
    public Input() {
        super(START_PART, null, ElementConfig.BLOCK_ELEMENT_LEVEL);
    }

    /**
     * Add an TextBlock to the Input Root on the same Level
     * add the firstLine
     *
     * @param line current Line in the Text
     * @return return generated TextBlock
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (line.equals("")) {
            return this;
        }

        BlockElement block = generateTextBlockSameLevel();
        block.addTextBlockToElem(line);
        return block;
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXStart(map, key, level, exportComment, exportSummary);
        StringBuilder text = map.get(key);
        String indentation = getIndentation(level);

        text.append(indentation).append(this.getStartPart()).append("{").append(this.content).append("}").append("\n");

        String newKey = this.content;
        map.put(newKey, new StringBuilder());
        if (this.children != null && !this.children.isEmpty()) {
            for (Element child : this.children) {
                child.toLaTeX(map, newKey, INIT_INDENTATION_LEVEL, exportComment, exportSummary);
            }
        }
        super.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }

    public String getStartPart() {
        return ElementConfig.INPUT.getStartPart();
    }
}
