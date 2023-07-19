package com.Application.Tree.elements;


import com.Application.Tree.Element;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 */
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


    /**
     * Content in sectioning is curly Brackets
     *
     * @param optionsString
     */
    public void setContent(String optionsString) {
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(optionsString);

        if (matcher.find()) {
            this.options = matcher.group(1);
        } else {
            this.options = null;
        }
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
    public int levelOfDeepestSectioningChild() {
        int deepestChildLevel = 0;
        for (Element child : this.childElements) {
            int childLevel = child.levelOfDeepestSectioningChild();
            deepestChildLevel = Math.max(deepestChildLevel, childLevel);
        }

        return deepestChildLevel + 1;
    }
}
