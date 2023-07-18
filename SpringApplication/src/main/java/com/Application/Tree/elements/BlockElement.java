package com.Application.Tree.elements;

import java.util.List;

public class BlockElement extends Child {

    private static final int BLOCK_ELEMENT_LEVEL = 12;

    public BlockElement(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex, BLOCK_ELEMENT_LEVEL);
    }
    @Override
    public List<String> toText() {
        return this.text;
    }
}
