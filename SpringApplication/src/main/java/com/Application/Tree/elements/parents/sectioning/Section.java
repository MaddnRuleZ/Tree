package com.Application.Tree.elements.parents.sectioning;


import com.Application.Tree.elements.Parent;

public class Section extends Parent {
    public static final String startPart = "\\section";
    public static final String endPart = null;
    public Section(int startIndex) {
        super(startPart, endPart, startIndex);
    }

}
