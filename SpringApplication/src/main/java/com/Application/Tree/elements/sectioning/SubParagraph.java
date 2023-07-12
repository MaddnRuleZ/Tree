package com.Application.Tree.elements.sectioning;

import com.Application.Tree.elements.Parent;

public class SubParagraph extends Parent {

    public static final String startPart = "\\subparagraph";
    public static final String endPart = null;

    public SubParagraph(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
