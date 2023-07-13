package com.Application.Tree.elements;


import com.Application.Tree.Element;
import com.Application.Tree.elements.Parent;

import java.util.UUID;

public class Root extends Parent {

    // err w/ coldstart by now
    public static final String startPart = "";
    public static final String endPart = null;

    public String[] startText;
    public Root() {
        super(startPart, endPart, 0,0);
    }

    public void addStartText(String[] startText) {
        this.startText = startText;
    }

    public String[] getStartText() {
        return this.startText;
    }

    public Element searchForID(UUID id) {
        //TODO
        return null;
    }



}
