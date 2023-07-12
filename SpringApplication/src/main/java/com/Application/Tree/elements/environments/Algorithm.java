package com.Application.Tree.elements.environments;

public class Algorithm extends Environment {

    public static final String startPart = "\\begin{algorithm}";
    public static final String endPart = "\\end{algorithm}";

    public Algorithm(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
