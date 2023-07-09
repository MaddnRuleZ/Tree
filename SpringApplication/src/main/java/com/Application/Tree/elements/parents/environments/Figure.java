package main.java.com.Application.Tree.elements.parents.environments;

import Tree.elements.Environment;

public class Figure extends Environment {

    public static final String startPart = "\\begin{figure}";
    public static final String endPart = "\\end{figure}";

    public Figure(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
