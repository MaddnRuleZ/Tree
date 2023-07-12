package com.Application.Tree.elements.parents.sectioning;

import com.Application.Tree.elements.Parent;

public class SubSubSection extends Parent {

    public static final String startPart = "\\subsubsection";
    public static final String endPart = null;

    public SubSubSection(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
