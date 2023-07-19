package com.Application.Tree.elements;

import java.util.List;

public class BlockElement extends Child {
    private static final int BLOCK_ELEMENT_LEVEL = 12;

    /**
     *
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
    public void addText(String line) {
        if (this.getParentElement() instanceof Figure) {
            Figure figure = (Figure) getParentElement();

            if (line.contains(Figure.CAPTION_IDENT)) {
                figure.addCaption(line);
            } else if (line.contains(Figure.GRAPHICS_IDENT)) {
                figure.setGraphics(line);
            } else {
                super.addText(line);
            }
        } else {
            super.addText(line);
        }
    }

    @Override
    public List<String> toText() {
        return this.text;
    }
}
