package com.application.tree.elements.parent;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.elements.roots.Root;

import java.util.Map;

/**
 * Sectioning Class
 * <p>
 * The Sectioning class represents a parent element that groups more elements within
 * <p>
 * A Sectioning element is defined by its startPart string and the hierarchical level (level) of the section.
 * It doesn't require an endPart, as it only specifies the start of the section.
 */
public class Sectioning extends Parent {

    /**
     * Constructor for creating a new Sectioning object with the specified startPart and level.
     *
     * @param startPart The startPart string that identifies the beginning of the section.
     * @param level     The hierarchical level of the section.
     */
    public Sectioning(String startPart, int level) {
        super(startPart, null, level);
    }

    @Override
    public int levelOfDeepestSectioningChild() {
        int deepestChildLevel = 0;
        for (Element child : this.children) {
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
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXStart(map, key, level, exportComment, exportSummary);
        String indentation = getIndentation(level);
        StringBuilder text = map.get(key);

        // \section[options]{content} or \section*{content}
        text.append(indentation).append(this.getStartPart());
        if(this.options != null && this.options.equals("*")) {
            text.append("*");
        } else if (this.options != null) {
            text.append("[").append(this.options).append("]");
        }
        text.append("{").append(this.content).append("}").append("\n");

        //children
        if (this.children != null && !this.children.isEmpty()) {
            for (Element child : this.children) {
                child.toLaTeX(map, key, level + 1, exportComment, exportSummary);
            }
        }

        super.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }

    /**
     * calculates Level of Section to determine the type of Sectioning
     * adds the calculated Level to the Root.MIN_LEVEL to get the correct Sectioning Type
     * @return String
     * @throws UnknownElementException if calculated Level is not known
     */
    @Override
    public String getStartPart() throws UnknownElementException{
        int level = Root.getInstance().getMinLevel() + this.calculateLevelFromElement() - 1;
        ElementConfig type = ElementConfig.getSectioningType(level);
        if (type == null) {
            throw new UnknownElementException(this.getClass().getSimpleName());
        }
        return type.getStartPart();
    }
}
