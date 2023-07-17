package com.Application.Tree.elements;


import com.Application.Tree.Element;

import java.util.UUID;

public class Sectioning extends Parent {

    /**
     * Create a new Sectioning Type
     *
     * @param startPart
     * @param startIndex
     * @param level
     */
    public Sectioning(String startPart, int startIndex, int level) {
        super(startPart, null, startIndex, level);
    }

    @Override
    public Element searchForID(UUID id) {
        if (this.getId().equals(id)) {
            return this;
        } else {
            for (Element child: this.getChildElements()) {
                Element foundElement = child.searchForID(id);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }
        return null;
    }

    @Override
    public int levelOfDeepestSectioningChild(boolean start) {
        int deepestChildLevel = 0;

        for (Element child : this.childElements) {
            int childLevel = child.levelOfDeepestSectioningChild(false);
            deepestChildLevel = Math.max(deepestChildLevel, childLevel);
        }
        if(!start) {
            deepestChildLevel++;

        }
        return deepestChildLevel;
    }
}
