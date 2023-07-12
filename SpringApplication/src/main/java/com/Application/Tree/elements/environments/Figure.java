package com.Application.Tree.elements.environments;

public class Figure extends Environment {

    public static final String startPart = "\\begin{figure}";
    public static final String endPart = "\\end{figure}";

    public Figure(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
