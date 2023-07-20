package com.Application.Tree.elements.root;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.elements.Element;

import java.util.List;
import java.util.Map;

public interface Roots {
    void addChild(Element childElement);

    void toLaTeX(Map<String, StringBuilder> map, String root) throws UnknownElementException;
}
