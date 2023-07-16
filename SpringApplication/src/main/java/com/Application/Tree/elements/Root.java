package com.Application.Tree.elements;

import com.Application.Tree.Element;
import com.Application.Tree.interfaces.Exportable;
import com.Application.Tree.interfaces.JsonParser;
import com.Application.Tree.interfaces.Roots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * This is the Singleton Function of the Root of the Whole LaTeX code.
 *
 */
public class Root implements JsonParser, Exportable, Roots {
    private final List<Element> childElements;
    private static Root instance;

    private int minLevel;

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

    public static boolean isInit() {
        return instance == null;
    }

    public void addChild(Element element) {
        this.childElements.add(element);
    }


    public void getChildElements() {
        //TODO
    }

    public Root createRoot() {
        //TODO
        return null;
    }

    public Element searchForID(UUID id) {
        //TODO
        return null;
    }

    //todo:
    @Override
    public String[] toText() {
        List<String> arrayList = new ArrayList<>();
        for (Element element: childElements) {
            arrayList.addAll(Arrays.asList(element.toText()));
        }
        return arrayList.toArray(new String[0]);
    }

    @Override
    public String toJson() {
        return null;
    }
}
