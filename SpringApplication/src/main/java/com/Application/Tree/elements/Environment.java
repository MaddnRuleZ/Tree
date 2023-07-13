package com.Application.Tree.elements;

import com.Application.Interpreter.TextFileReader;
import com.Application.Tree.Element;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Environment extends Parent {

    public Environment(String startPart, String endPart, int startIndex, int level) {
        super(startPart, endPart, startIndex, level);
    }

    @Override
    public Element searchForID(UUID id, int level) {
        if (this.getId().equals(id)) {
            return this;
        } else {
            for (Element child: this.getChildElements()) {
                Element foundElement = child.searchForID(id, level);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }
        return null;
    }
}
