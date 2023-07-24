package com.Application.Tree.elements.parent;


import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.elements.roots.Root;

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
        super.toLaTeXStart(map, key);

        StringBuilder text = map.get(key);

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

        super.toLaTeXEnd(map, key);
    }

    /**
     * calculates Level of Section to determine the type of Sectioning
     * adds the calculated Level to the Root.MIN_LEVEL to get the correct Sectioning Type
     * @return String
     * @throws UnknownElementException if calculated Level is not known
     */
    @Override
    public String getStartPart() throws UnknownElementException{
        int level = Root.MIN_LEVEL + this.calculateLevelFromElement();
        ElementConfig type = ElementConfig.getSectioningType(level);
        if (type == null) {
            throw new UnknownElementException(this.getClass().getSimpleName());
        }
        return type.getStartPart();
    }
}
