package com.application.tree.elements.childs;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.elements.parent.Figure;

import java.util.Map;

/**
 * BlockElement Class
 *
 * The BlockElement class represents a child element within the LaTeX Code.
 * BlockElements are undetected Blocks of Text which represent no detected Structure Element
 */
public class BlockElement extends Child {

    /**
     * Call the constructor of the parent class (Child) with null startPart and endPart and the block element level.
     */
    public BlockElement() {
        super(null, null, ElementConfig.BLOCK_ELEMENT_LEVEL);
        this.type = this.getClass().getSuperclass().getSimpleName();
    }

    /**
     * add a TextBlock to the Element and generate it on the same Level
     * Check if Parent is Figure -> this BlockElement inside Figure
     * add Caption / GraphicString to the Figure when in current Line
     *
     * @param line line to Scan for Summary Comment or NewLine
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (newLine.checkLineForNewLineCharacters(line)) {
            newLine.extractContent(line);
            return generateTextSameLevel();
        } else {
            if (!summary.extractContent(line) && !comment.extractContent(line)) {
                if (parentElement instanceof Figure figure) {
                    if (!figure.setGraphics(line) && !figure.addCaption(line)) {
                        this.text.add(line);
                    }
                } else {
                    this.text.add(line);
                }
            }
            return this;
        }
    }
    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level) throws UnknownElementException {
        super.toLaTeXStart(map, key, level);
        String indentation = getIndentation(level);

        StringBuilder text = map.get(key);
        text.append(indentation).append(indentation).append(this.content);

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key, level);
        }

        super.toLaTeXEnd(map, key, level);
    }
}
