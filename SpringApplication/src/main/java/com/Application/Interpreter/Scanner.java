package com.Application.Interpreter;

import com.Application.Tree.Element;
import com.Application.Tree.elements.Input;
import com.Application.Tree.elements.Parent;
import com.Application.Tree.elements.Root;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.interfaces.Roots;

/**
 *
 *
 */
public class Scanner {
    private final String[] text;
    private final Roots root;

    /**
     *
     * @param text
     */
    public Scanner(String[] text) {
        if (Root.isInit()) {
            root = Root.getInstance();
        } else {
            root = new Input();
        }
        this.text = text;
    }

    /**
     * todo, Ebene N Root; N+1 ret Element?, instance of "input"?
     * scan the given Document for Latex Structure Elements and return the root Element for this Part of the Tree
     *
     * @return root Element of Tree
     */
    public Roots parseDocument() {
        boolean firstElementFound = false;
        Element currElement = null;

        for (int i = 0; i < text.length; i++) {
            if (text[i].contains("\\")) {
                try {
                    currElement = scanLine(currElement, i);
                    if (currElement != null && !firstElementFound) {
                        firstElementFound = true;
                        // root.addStartText(TextFileReader.extractStrings(text, 0, i - 1));
                    }
                } catch(Exception e) {
                    // throw new ElementNotFoundException("Fatal Error while creating Element, in line" + i);
                    System.out.println("### Error in line " + i + ": " + e.getMessage());
                    e.printStackTrace();
                }

            } else {
                // Element is BlockElement?
                // = new BLockElement, enter Index

            }
        }

        if (currElement != null) {
            currElement.scanElementTextForSubElements(text, text.length);
        }
        return root;
    }

    /**
     * Scan the current line and create an new Element or Close one
     *
     * @param lastElement last Element that was created
     * @param index current index of the text
     * @return the New Created Text Element with parent and child hierachie
     */
    private Element scanLine(final Element lastElement, final int index) {
        if (lastElement != null && lastElement.getEndPart() != null && text[index].contains(lastElement.getEndPart())) {
            lastElement.scanElementTextForSubElements(text, index);
            // ret null -> so Enviroments better
            return lastElement;

        } else {
            // generate text of last?
            Element newElement = ElementConfig.createElement(this.text[index], index);

            if (newElement != null) {
                if (lastElement == null) {
                    root.addChild(newElement);
                    // -> root + 1 Elemente haben kein Parent, unnavigierbar? alternativ check Instance
                    // element.setParent(root);

                } else if (newElement.getLevel() > lastElement.getLevel()) {
                    lowerLevel(lastElement, index, newElement);

                } else if (newElement.getLevel() == lastElement.getLevel()) {
                    sameLevel(lastElement, index, newElement);

                } else {
                    higherLevel(lastElement, index, newElement);
                }
                return newElement;
            } else {
                return lastElement;
            }
        }
    }

    /**
     * add the new Element as Child of the lastElement
     * start the Generation of the Elements in between
     *
     * @param lastElement lastElement created
     * @param index index in the current Text
     * @param newElement currentElement created
     */
    private void lowerLevel(Element lastElement, int index, Element newElement) {
        lastElement.scanElementTextForSubElements(text, index);
        setParentChild(lastElement, newElement);
    }

    /**
     * add the newElement to the same Parent as the lastElement
     *
     * @param lastElement lastElement created
     * @param index index in the current Text
     * @param newElement currentElement created
     */
    private void sameLevel(Element lastElement, int index, Element newElement) {
        lastElement.scanElementTextForSubElements(text, index);
        Element parent = lastElement.getParentElement();

        if (parent == null) {
            root.addChild(newElement);
        } else {
            setParentChild(parent, newElement);
        }
    }

    /**
     * higher lvl, navigate tree struct up, search matching Parent to add Element to
     *
     * @param lastElement lastElement created
     * @param index index in the current Text
     * @param newElement currentElement created
     */
    private void higherLevel(Element lastElement, int index, Element newElement) {
        Element searchElem = lastElement;

        while (searchElem != null && searchElem.getLevel() >= newElement.getLevel()) {
            searchElem.scanElementTextForSubElements(text, index);
            searchElem = searchElem.getParentElement();
        }

        if (searchElem == null) {
            root.addChild(newElement);
        } else if (searchElem.getLevel() == newElement.getLevel()) {
            Element parent = searchElem.getParentElement();
            setParentChild(parent, newElement);
        } else {
            setParentChild(searchElem, newElement);
        }
    }


    /**
     * set the double linked Connection between two Elements
     * set the Parent as Parent for the Child,
     * set the Child as Child for the Parent,
     *
     * @param parent Parent Element
     * @param child Child Element
     */
    private void setParentChild(Element parent, Element child) {
        child.setParent(parent);
        if (parent instanceof Parent parentInst) {
            parentInst.addChild(child);
        } else {
            System.out.println("### FATAL ERROR, SEE SCANNER CLASS");
        }
    }
}
