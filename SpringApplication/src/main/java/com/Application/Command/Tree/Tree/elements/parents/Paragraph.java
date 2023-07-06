package Tree.elements.parents;

import Tree.elements.Parent;

public class Paragraph extends Parent {

    public static final String startPart = "\\paragraph";
    public static final String endPart = null;

    public Paragraph(int startIndex) {
        super(startPart, endPart, startIndex);
    }
}
