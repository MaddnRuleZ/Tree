package com.Application.Tree.elements.parent;


import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.ElementConfig;

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
            this.newLine.toLaTeX(map, key);
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
            throw new UnknownElementException(this.getClass().getSimpleName());
        }
        return type.getStartPart();
    }
}
