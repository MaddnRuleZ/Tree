package main.java.com.Application.Tree.elements.parents.environments;

import Tree.elements.Child;
import Tree.elements.Environment;

public class Equation extends Environment {

    public static final String startPart = "\\begin{equation}";
    public static final String endPart = "\\end{equation}";
    public Equation(int startIndex) {
        super(startPart, endPart, startIndex);
    }

}
