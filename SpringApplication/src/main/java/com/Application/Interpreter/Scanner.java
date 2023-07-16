package com.Application.Interpreter;

import com.Application.Tree.Element;
import com.Application.Tree.elements.*;
import com.Application.Tree.interfaces.Roots;

/**
 * Scanner Class for Disassemble a Document into its Latex Structur Elements
 */
public class Scanner {
    private final String[] text;
    private final Roots root;

    /**
     * Create a new Instance of the Scanner for Scanning a Text on Elements
     *
     * @param text Text the Scanner shall Scan
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
     * todo wird noch geändert
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

                if (currElement != null && !currElement.isTextBlock() && currElement instanceof BlockElement) {
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

        // todo Ending still check for instance
        if (currElement != null) {
            // End Last Open
            currElement.assignTextToTextBlock(text, text.length);
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
            // End Environment
            lastElement.assignTextToTextBlock(text, index);
            return lastElement.getParentElement();

        } else {
            // End TextBlock
            if (lastElement != null && lastElement.isTextBlock()) {
                lastElement = lastElement.assignTextToTextBlock(text, index);
            }
            Element newElement = ElementConfig.createElement(this.text[index], index);
            if (newElement != null) {
                newElement.setOptions(this.text[index]);

                if (lastElement == null) {
                    root.addChild(newElement);
                    if (newElement instanceof Input) {
                        Input inputRoot = (Input) root;
                        newElement.setParent(inputRoot);
                    }

                } else if (newElement.getLevel() > lastElement.getLevel()) {
                    lowerLevel(lastElement, newElement);

                } else if (newElement.getLevel() == lastElement.getLevel()) {
                    sameLevel(lastElement, newElement);

                } else {
                    higherLevel(lastElement, newElement);
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
     * @param newElement currentElement created
     */
    private void lowerLevel(Element lastElement, Element newElement) {
        setParentChild(lastElement, newElement);
    }

    /**
     * add the newElement to the same Parent as the lastElement
     *
     * @param lastElement lastElement created
     * @param newElement currentElement created
     */
    private void sameLevel(Element lastElement, Element newElement) {
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
     * @param newElement currentElement created
     */
    private void higherLevel(Element lastElement, Element newElement) {
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
