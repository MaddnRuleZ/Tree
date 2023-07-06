package Tree.elements;

import Tree.Element;

import java.util.ArrayList;
import java.util.List;

public class Parent extends Element {
    private final List<Element> childElements;
    public Parent(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex);
        childElements = new ArrayList<>();
    }

    public void addChild(Element element) {
        this.childElements.add(element);
    }

    public boolean addChildAfter(Element newChild, Element previousChild) {
        return false;
    }

    public Element getPreviousElement(Element element) {
        return null;
    }

    public boolean removeChild(Element element) {
        return false;
    }

    public List<Element> getChildElements() {
        return this.childElements;
    }

}
