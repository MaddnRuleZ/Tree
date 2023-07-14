package com.Application.Tree.elements;


import com.Application.Tree.Element;

import java.util.UUID;

public class Sectioning extends Parent {
    public Sectioning(String startPart, int startIndex, int level) {
        super(startPart, null, startIndex, level);
    }


    @Override
    public Element searchForID(UUID id, int level) {
        if (this.getId().equals(id)) {
            return this;
        } else {
            for (Element child: this.getChildElements()) {
                Element foundElement = child.searchForID(id, level + 1);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }
        return null;
    }
}
