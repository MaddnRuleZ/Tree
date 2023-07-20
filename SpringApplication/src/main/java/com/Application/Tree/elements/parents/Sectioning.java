package com.Application.Tree.elements.parents;


import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.elements.Element;

import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 *
 *
 */
public class Sectioning extends Parent {

    /**
     * Create a new Sectioning Type
     *
     * @param startPart
     * @param level
     */
    public Sectioning(String startPart, int level) {
        super(startPart, null, level);
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

    @Override
    public int calculateLevelFromElement() {
        Parent parent = this.getParentElement();
        if (parent == null) {
            return 1;
        }
        return parent.calculateLevelFromElement() + 1;
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n");

        if(this.summary != null) {
            this.summary.toLaTeX(map, key);
        }
        if(this.comment != null) {
            this.comment.toLaTeX(map, key);
        }

        text.append(this.getStartPart());

        if(this.options != null && this.options.equals("*")) {
            text.append("*");
        } else if (this.options != null) {
            text.append("[").append(this.options).append("]");
        }
        text.append("{").append(this.content).append("}");

        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key);
            }
        }

        if(this.newLine != null) {
            text.append(this.newLine);
        }
    }

    /**
     * calculates Level of Section to determine the type of Sectioning
     * @return String
     * @throws UnknownElementException if calculated Level is not known
     */
    //TODO add minLevel, so that it doesn't start with 1
    @Override
    public String getStartPart() throws UnknownElementException{
        int level = this.calculateLevelFromElement();
        ElementConfig type = ElementConfig.getSectioningType(level);
        if(type == null) {
            throw new UnknownElementException("Something went wrong while generating the LaTeX code for the element: " + this);
        }
        return type.getStartPart();
    }
}
