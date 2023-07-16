package com.Application.Interpreter;

import com.Application.Tree.Element;
import com.Application.Tree.elements.*;
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
     * todo wird noch gepolished
     * scan the given Document for Latex Structure Elements and return the root Element for this Part of the Tree
     *
     * @return root Element of Tree
     */
    public Roots parseDocument() {
        boolean firstElementFound = false;
        boolean newBlockAvailable = false;
        Element currElement = null;

        for (int i = 0; i < text.length; i++) {
            if (text[i].contains("\\")) {
                currElement = scanLine(currElement, i);
                if (currElement != null && !firstElementFound) {
                    firstElementFound = true;
                }

                if (currElement != null && currElement.getStartPart() != null && currElement instanceof BlockElement) {
                    // filter env
                    newBlockAvailable = false;
                } else {
                    newBlockAvailable = true;
                }

            } else {
                // start new TextBLock
                if (newBlockAvailable) {
                    BlockElement textBlockElement = new BlockElement(null, null, i);
                    textBlockElement.setParent(currElement);

                    if (currElement != null) {
                        Parent currentElement = (Parent) currElement;
                        currentElement.addChild(textBlockElement);
                        currElement = textBlockElement;
                    }
                    newBlockAvailable = false;
                }
            }
        }

        // todo Ending still fucked
        if (currElement != null) {
            currElement.assignTextToBlock(text, text.length);
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
    private Element scanLine(Element lastElement, final int index) {
        if (lastElement != null && lastElement.getEndPart() != null && text[index].contains(lastElement.getEndPart())) {
            lastElement.assignTextToBlock(text, index);
            return lastElement.getParentElement();

        } else {
            if (lastElement != null && lastElement.getStartPart() == null) {
                lastElement = lastElement.assignTextToBlock(text, index);
            }
            Element newElement = ElementConfig.createElement(this.text[index], index);
            if (newElement != null) {
                newElement.setOptions(this.text[index]);

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
