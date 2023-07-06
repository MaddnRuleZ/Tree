package Tree.elements.childs;

import Tree.elements.Child;

public class Equation extends Child {

    public static final String startPart = "\\begin{equation}";
    public static final String endPart = "\\end{equation}";

    public Equation(int startIndex) {
        super(startPart, endPart, startIndex);
    }

}
