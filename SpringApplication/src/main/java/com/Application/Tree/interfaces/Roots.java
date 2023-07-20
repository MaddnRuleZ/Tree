package com.Application.Tree.interfaces;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;

import java.util.List;
import java.util.Map;

public interface Roots {
    void addChild(Element childElement);

    void toLaTeX(Map<String, StringBuilder> map, String root) throws UnknownElementException;
}
