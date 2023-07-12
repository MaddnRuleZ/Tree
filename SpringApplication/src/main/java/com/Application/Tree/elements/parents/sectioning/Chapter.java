package main.java.com.Application.Tree.elements.parents.sectioning;

import Tree.elements.Parent;

public class Chapter extends Parent {

    public static final String startPart = "\\chapter";
    public static final String endPart = null;

    public Chapter(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}