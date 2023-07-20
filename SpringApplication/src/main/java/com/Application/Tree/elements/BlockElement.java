package com.Application.Tree.elements;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.additionalInfo.NewLine;

import java.util.List;
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
    public Element addText(String line) {
        if (NewLine.checkLineForNewLineCharacters(line)) {
            helperFunc(line);
            BlockElement textBlockElement = new BlockElement(null, null);
            Parent parent = (Parent) this.parentElement;
            textBlockElement.setParent(parent);
            parent.addChild(textBlockElement); // here
            return textBlockElement;
        } else {
            return helperFunc(line);
        }
    }

    private Element helperFunc(String line) {
        if (this.getParentElement() instanceof Figure) {
            Figure figure = (Figure) getParentElement();

            if (line.contains(Figure.CAPTION_IDENT)) {
                figure.addCaption(line);
            } else if (line.contains(Figure.GRAPHICS_IDENT)) {
                figure.setGraphics(line);
            } else {
                text.add(line);
            }
            return this;

        } else {
            text.add(line);
            return this;
        }
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);


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
