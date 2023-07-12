package com.Application.Tree.elements.EnvElements;

import com.Application.Tree.elements.Child;
import com.Application.Tree.elements.Parent;


/**
 * notes to stuff still todo
 *
 *
 */
// Make this child, cannot have children itself and is struct element that can be moved everywhere among the half ordnung
public class Label extends Child {

    public static final String startPart = "\\label";
    public static final String endPart = null;

    public Label() {
        super(startPart, endPart, 0);
    }


}
