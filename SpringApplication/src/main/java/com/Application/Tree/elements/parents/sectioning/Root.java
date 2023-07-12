package com.Application.Tree.elements.parents.sectioning;


import com.Application.Tree.elements.Parent;

public class Root extends Parent {

    // err w/ coldstart by now
    public static final String startPart = "";
    public static final String endPart = null;

    public String[] startText;
    public Root() {
        super(startPart, endPart, 0);
    }

    public void addStartText(String[] startText) {
        this.startText = startText;
    }

    public String[] getStartText() {
        return this.startText;
    }

}
