package com.Application.Tree.elements.environments;

import com.Application.Tree.elements.Environment;

public class Equation extends Environment {

    public static final String startPart = "\\begin{equation}";
    public static final String endPart = "\\end{equation}";
    public Equation(int startIndex) {
        super(startPart, endPart, startIndex);
    }

}
