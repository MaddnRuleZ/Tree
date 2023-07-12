package com.Application.Tree.elements.parents.sectioning;

import com.Application.Tree.elements.Parent;

public class Paragraph extends Parent {

    public static final String startPart = "\\paragraph";
    public static final String endPart = null;

    public Paragraph(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
