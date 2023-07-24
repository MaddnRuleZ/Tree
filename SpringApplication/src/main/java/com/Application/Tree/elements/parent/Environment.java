package com.Application.Tree.elements.parent;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.childs.BlockElement;

import java.util.Map;
import java.util.UUID;

public class Environment extends Parent {

    /**
     * Create a new Environment
     *
     * @param startPart
     * @param endPart
     * @param level
     */
    public Environment(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
    }

    /**
     * Add an new TextBlock to the Environment.
     * On the Same Level if
     *
     * @param line line to Scan for Summary Comment or NewLine
     * @return
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (this.childElements.size() == 0) {
            BlockElement block = generateTextBlockAsChild();
            block.addTextBlockToElem(line);
            return block;
        } else {
            BlockElement block = generateTextSameLevel();
            block.addTextBlockToElem(line);
            return block;
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
    //TODO environment end und start part
    public void toLaTeX(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        super.toLaTeXStart(map, key);

        StringBuilder text = map.get(key);

        text.append(this.getStartPart());
        if(this.options != null) {
            text.append("[").append(this.options).append("]");
        }
        text.append("\n");
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key);
            }
        }
        text.append(this.getEndPart());
        text.append("\n");

        super.toLaTeXEnd(map, key);
    }

}
