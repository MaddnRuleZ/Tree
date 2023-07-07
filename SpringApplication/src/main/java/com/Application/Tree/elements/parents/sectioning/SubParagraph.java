package main.java.com.Application.Tree.elements.parents.sectioning;

import Tree.elements.Parent;

public class SubParagraph extends Parent {

    public static final String startPart = "\\subparagraph";
    public static final String endPart = null;

    public SubParagraph(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
