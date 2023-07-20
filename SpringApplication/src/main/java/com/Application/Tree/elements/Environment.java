package com.Application.Tree.elements;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;

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

    @Override
    //TODO environment end und start part
    public void toLaTeX(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n");

        if(this.summary != null) {
            this.summary.toLaTeX(map, key);
        }
        if(this.comment != null) {
            this.comment.toLaTeX(map, key);
        }

        text.append(this.getStartPart()).append("{").append(this.content).append("}");
        text.append("\n");
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key);
            }
        }
        text.append(this.getEndPart());
        text.append("\n");

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key);
        }
    }
/**
    @Override
    public String getStartPart() {
        return ElementConfig.ENVIRONMENT.getStartPart();
    }

    @Override
    public String getEndPart() {
        return ElementConfig.ENVIRONMENT.getEndPart();
    }
    **/
}
