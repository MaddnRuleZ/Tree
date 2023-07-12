package com.Application.Tree.elements.EnvElements;

import com.Application.Tree.elements.Child;
import com.Application.Tree.elements.Parent;

public class Caption extends Child {

    public static final String startPart = "\\caption";
    public static final String endPart = null;

    /**
     *
     */
    public Caption() {
        super(startPart, endPart, 0);
    }

    public String getCaption() {
        return this.caption;
    }

}
