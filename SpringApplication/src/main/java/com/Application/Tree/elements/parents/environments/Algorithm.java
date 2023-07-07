package main.java.com.Application.Tree.elements.parents.environments;

import Tree.elements.Environment;

public class Algorithm extends Environment {

    public static final String startPart = "\\begin{algorithm}";
    public static final String endPart = "\\end{algorithm}";

    public Algorithm(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
