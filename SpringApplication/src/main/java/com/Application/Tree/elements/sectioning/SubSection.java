package com.Application.Tree.elements.sectioning;


import com.Application.Tree.elements.Parent;

public class SubSection extends Parent {

    public static final String startPart = "\\subsection";
    public static final String endPart = null;

    public SubSection(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
