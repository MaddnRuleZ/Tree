package com.Application.Tree.elements;

import java.util.List;

public class BlockElement extends Child {
    private static final int BLOCK_ELEMENT_LEVEL = 12;

    public BlockElement(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex, BLOCK_ELEMENT_LEVEL);
    }

    @Override
    public void addText(String line) {
        if (this.getParentElement() instanceof Figure) {
            Figure figure = (Figure) getParentElement();

            if (line.contains("\\caption")) {
                figure.addCaption(line);
            } else if(line.contains("\\includegraphics")) {
                figure.setGraphics(line);
            }
        }
        super.addText(line);

    }

    @Override
    public List<String> toText() {
        return this.text;
    }
}
