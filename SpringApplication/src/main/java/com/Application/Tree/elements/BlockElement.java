package com.Application.Tree.elements;

public class BlockElement extends Child {

    /* ACHTUNG HIER SIND NOCH ÄNDERUNGEN ZU MACHEN */
    private static final int BLOCK_ELEMENT_LEVEL = 12;

    public BlockElement(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex, BLOCK_ELEMENT_LEVEL);

    }
}
