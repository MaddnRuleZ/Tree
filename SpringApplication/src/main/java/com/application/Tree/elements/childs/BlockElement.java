package com.application.Tree.elements.childs;

import com.application.Exceptions.UnknownElementException;
import com.application.Tree.Element;
import com.application.Tree.elements.parent.Figure;

import java.util.Map;

/**
 * BlockElement Class
 *
 * The BlockElement class represents a child element within the LaTeX Code.
 * BlockElements are undetected Blocks of Text which represent no detected Structure Element
 */
public class BlockElement extends Child {
    private static final int BLOCK_ELEMENT_LEVEL = 12;

    /**
     * Call the constructor of the parent class (Child) with null startPart and endPart and the block element level.
     */
    public BlockElement() {
        super(null, null, BLOCK_ELEMENT_LEVEL);
        this.type = this.getClass().getSuperclass().getSimpleName();
    }

    /**
     * Check if Parent is Figure -> BlockElement inside Figure
     * add Caption / GraphicString to the Figure
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
                // todo Enter this: if (parentElement.getStartPart().equals(ElementConfig.FIGURE.getStartPart())) { getStartPart wirft eine Exeption -> S fragen
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
