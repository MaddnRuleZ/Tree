package com.Application.Tree.elements.sectioning;

import com.Application.Tree.elements.Parent;


public class Part extends Parent {

    public static final String startPart = "\\part";
    public static final String endPart = null;
    public Part(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
