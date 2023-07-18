package com.Application.Tree.elements;

import com.Application.Tree.Element;
import com.Application.Tree.interfaces.Exportable;
import com.Application.Tree.interfaces.JsonParser;
import com.Application.Tree.interfaces.Roots;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;


/**
 * This is the Singleton Function of the Root of the Whole LaTeX code.
 */
public class Root implements JsonParser, Exportable, Roots {
    private final List<Element> childElements;
    private static Root instance;

    private List<String> startHeaderText;

    private static int MIN_LEVEL = 0;

    private Root() {
        // Private constructor to prevent instantiation from outside the class
        childElements = new ArrayList<>();
    }

    /**
     * Singleton get Instance of current Root
     *
     * @return instance of Root Object
     */
    public static Root getInstance() {
        if (instance == null) {
            synchronized (Root.class) {
                if (instance == null) {
                    instance = new Root();
                }
            }
        }
        return instance;
    }

    /**
     * Update the Minimum Level Cap from the Complete file
     *
     * @param newLevelCap level of new Created Element
     */
    public static void updateLevelCap(int newLevelCap) {
        if (newLevelCap < MIN_LEVEL) {
            MIN_LEVEL = newLevelCap;
        }
    }

    /**
     * Check if the Root is already initialized
     *
     * @return true if Root is init, else false
     */
    public static boolean isInit() {
        return instance == null;
    }


    public void addChild(Element element) {
        this.childElements.add(element);
    }

    public Root createRoot() {
        //TODO
        return null;
    }

    /**
     * searches for the element with the given id
     *
     * @param id to search for
     * @return found Element or null
     */
    public Element searchForID(UUID id) {
        for (Element element: childElements) {
            Element foundElement = element.searchForID(id);
            if (foundElement != null) {
                return foundElement;
            }
        }
        return null;
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public List<String> toText() {
        List<String> text = new ArrayList<>();
        // todo @S add the startHeader here
        for (Element element: childElements) {
            text.addAll(element.toText());
        }
        return text;
    }

    /**
     * calculates the level of the calling Element from bottom to top
     * @return level of the calling Element
     */
    public int calculateLevelFromElement() {
        return -1;
    }

}
