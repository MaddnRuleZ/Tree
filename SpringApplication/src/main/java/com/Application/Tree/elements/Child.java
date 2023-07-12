package com.Application.Tree.elements;

import com.Application.Tree.Element;

/**
 *
 */
public class Child extends Element {

    /**
     *
     * @param startPart
     * @param endPart
     * @param startIndex
     */
    public Child(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex);
    }

    @Override
    public boolean validateIndicTextGeneration() {
        return true;
    }

    /**
     *
     * @param child element to add
     */
    @Override
    public boolean addChild(Element child) {
        System.out.println("Error Tried to add Child to Child, see Class, u guessed it: Child");
        // fix? -> change to bool, ret f here?
        return false;
    }

    @Override
    public String[] toText() {
        System.out.println("Child not implemented yeet");
        return null;
    }

    @Override
    public String[] toTextAdvanced() {
        if (text != null) {
            return text;
        }
        return null;
    }

    /*
    @Override
    public String[] toTextAdvanced() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(this.getOptions());

        if (this.comment.getContent() != null) {
            arrayList.addAll(this.comment.getContent());
        }

        if (this.summary.getContent() != null) {
            arrayList.addAll(this.summary.getContent());
        }

        if (newLine.getTextParts() != null) {
            for (List<String> list: newLine.getTextParts()) {
                arrayList.addAll(list);
            }
        }
        return arrayList.toArray(new String[0]);
    }
    */
}
