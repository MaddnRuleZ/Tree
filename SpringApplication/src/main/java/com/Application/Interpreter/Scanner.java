package com.Application.Interpreter;

import com.Application.Tree.Element;
import com.Application.Tree.elements.*;
import com.Application.Tree.elements.roots.Input;
import com.Application.Tree.elements.parent.Parent;
import com.Application.Tree.elements.roots.Root;
import com.Application.Tree.elements.roots.Roots;

/**
 * Scanner Class for Disassemble a Document into its Latex Structur Elements
 *
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
     * Parse the Document by scanning the line
     * if a new Element is created or Add the text to the current last TextBlockElement
     *
     * @return root Element of Tree
     */
    public Roots parseDocument() {
        Element lastElement = null;

        for (int i = 0; i < text.length; i++) {
            Element newElement = scanLine(lastElement, i);
            if (newElement != null) {
                lastElement = newElement;

            } else {
                lastElement = lastElement.addTextBlockToElem(text[i]);
            }
        }
        return root;
    }

    /**
     * Scan the line if there is a new Structure Element
     *
     *
     *
     * @param lastElement last Element that was created
     * @param index current index of the text
     * @return the New Created Text Element with parent and child hierachie
     */
    private Element scanLine(Element lastElement, final int index) {
        String currentLine = text[index];
        // End Environment
        if (lastElement != null && lastElement.getParentElement() != null &&  lastElement.getParentElement().getEndPart() != null
                && currentLine.contains(lastElement.getParentElement().getEndPart())) {

            return lastElement.getParentElement();
        } else {
            Element newElement = ElementConfig.createElement(currentLine);
            if (newElement != null) {
                newElement.setOptions(currentLine);
                newElement.setContent(currentLine);
                if (lastElement == null) {
                    root.addChild(newElement);
                    if (newElement instanceof Input) {
                        Input inputRoot = (Input) root;
                        newElement.setParent(inputRoot);
                    }
                } else if (newElement.getLevel() > lastElement.getLevel()) {
                    setParentChild(lastElement, newElement);

                } else if (newElement.getLevel() == lastElement.getLevel()) {
                    sameLevel(lastElement, newElement);

                } else {
                    higherLevel(lastElement, newElement);
                }
                return newElement;
            } else {
                return null;
            }
        }
    }

    /**
     * add the newElement to the same Parent as the lastElement
     *
     * @param lastElement lastElement created
     * @param newElement currentElement created
     */
    private void sameLevel(Element lastElement, Element newElement) {
        Parent parent = lastElement.getParentElement();

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
            Parent parent = searchElem.getParentElement();
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
        Parent parentChecked = (Parent) parent;
        if (parentChecked != null) {
            child.setParent(parentChecked);
            parentChecked.addChild(child);
        } else {
            System.out.println("Error: Element is no Parent!");
        }
    }
}
