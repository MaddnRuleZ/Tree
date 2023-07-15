package com.Application.Tree.elements;

import com.Application.Tree.Element;
import com.Application.Tree.interfaces.Exportable;
import com.Application.Tree.interfaces.JsonParser;

import java.util.List;
import java.util.UUID;

/**
 *
 *
 */
public class Root implements JsonParser, Exportable {
    private static Root instance;

    private List<Element> childElements;

    private int minLevel;

    private Root() {
        // Private constructor to prevent instantiation from outside the class
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

    @Override
    public String[] toText() {
        return new String[0];
    }

    @Override
    public String toJson() {
        return null;
    }


}
