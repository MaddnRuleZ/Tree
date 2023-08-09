package com.application.tree.elements.roots;

import com.application.tree.Element;

import java.util.List;

public interface Roots {
    void addChild(Element childElement);

    List<Element> getChildren();
}
