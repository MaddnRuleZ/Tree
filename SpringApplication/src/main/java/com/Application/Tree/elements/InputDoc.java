package com.Application.Tree.elements;

/**
 * Todo, implement into Parser? for Recursion detection
 */
public class InputDoc extends Parent {

    public static final String startPart = "\\input";
    public static final String endPart = null;

    // rm index?
    public InputDoc(int startIndex) {
        super(startPart, endPart, startIndex);
    }

}
