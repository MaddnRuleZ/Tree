package com.Application.Tree.elements;

import com.Application.Tree.Element;
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

    @Override
    public Element addText(String line) {
        BlockElement textBlockElement = new BlockElement(null, null);
        textBlockElement.setParent(this);
        this.addChild(textBlockElement);
        textBlockElement.addText(line);
        return textBlockElement;
        /*
        this.parentElement = textBlockElement;
        Parent parent = (Parent) this.parentElement;
        parent.addChild(textBlockElement);
        textBlockElement.setParent(this);
        */
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
}
