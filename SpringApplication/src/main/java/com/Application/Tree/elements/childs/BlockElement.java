package com.Application.Tree.elements.childs;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.additionalInfo.NewLine;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.elements.parent.Figure;

import java.util.Map;

public class BlockElement extends Child {
    private static final int BLOCK_ELEMENT_LEVEL = 12;

    /**
     *
     * @param startPart
     * @param endPart
     */
    public BlockElement(String startPart, String endPart) {
        super(startPart, endPart, BLOCK_ELEMENT_LEVEL);
    }

    /**
     * Check if Parent is Figure -> BlockElement inside Figure
     * add Caption / GraphicString to the Figure
     *
     * @param line line to Scan for Summary Comment or NewLine
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (NewLine.checkLineForNewLineCharacters(line)) {
            newLine.extractContent(line);
            return generateTextSameLevel();
        } else {
            if (!summary.extractContent(line) && !comment.extractContent(line)) {
                // todo Enter this: if (parentElement.getStartPart().equals(ElementConfig.FIGURE.getStartPart())) { getStartPart wirft eine Exeption -> S fragen
                    if (parentElement instanceof Figure) {
                    Figure figure = (Figure) parentElement;
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
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        super.toLaTeXStart(map, key);

        StringBuilder text = map.get(key);
        text.append(this.content);

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key);
        }

        super.toLaTeXEnd(map, key);
    }
}
