package Tree.elements.parents;

import Tree.elements.Parent;

public class Part extends Parent {

    public static final String startPart = "\\part";
    public static final String endPart = null;
    public Part(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
