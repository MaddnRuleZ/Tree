package com.Application.Tree.elements;

import com.Application.Tree.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Parent extends Element {
    private final List<Element> childElements;

    public Parent(String startPart, String endPart, int startIndex, int level) {
        super(startPart, endPart, startIndex, level);
        childElements = new ArrayList<>();
    }


    @Override
    public boolean validateIndicTextGeneration() {
        return this.text == null;
    }

    @Override
    public Element searchForID(UUID id, int level) {
        // TODO in Environment -> level ändert sich nicht
        // TODO in Sectioning -> level ändert sich
        return null;
    }

    public boolean addChild(Element element) {
        this.childElements.add(element);
        return true;
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

    @Override
    public String[] toText() {
        List<String>  arrayList = new ArrayList<>();
        String[] curr = this.getText();

        if (curr != null) {
            arrayList.add(this.getOptions());

            if (this.comment.getContent() != null) {
                arrayList.addAll(this.comment.getContent());
            }

            if (this.summary.getContent() != null) {
                arrayList.addAll(this.summary.getContent());
            }

            if (newLine.getTextParts() != null) {
                for (List<String> list: newLine.getTextParts()) {
                    if (list != null) {
                        arrayList.addAll(list);
                    }
                }
            }
        }

        for (Element element: childElements) {
            arrayList.addAll(Arrays.asList(element.toText()));
        }
        return arrayList.toArray(new String[0]);
    }
}