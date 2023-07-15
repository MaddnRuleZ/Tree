package com.Application.Interpreter;

import com.Application.Tree.Element;
import com.Application.Tree.elements.Root;
import com.Application.Tree.elements.ElementConfig;

/**
 *
 */
public class Scanner {
    private final String[] text;
    private final Root root;

    /**
     *
     * @param text
     */
    public Scanner(String[] text) {
        root = new Root();
        this.text = text;
    }

    /**
     * scan the given Document for Latex Structure Elements and return the root Element for this Part of the Tree
     *
     * @return root Element of Tree
     */
    public Root parseDocument() {
        boolean firstElementFound = false;
        Element currElement = null;

        for (int i = 0; i < text.length; i++) {
            if (text[i].contains("\\")) {
                try {
                    currElement = scanLine(currElement, i);
                    if (currElement != null && !firstElementFound) {
                        firstElementFound = true;
                        root.addStartText(TextFileReader.extractStrings(text, 0, i - 1));
                    }
                } catch(Exception e) {
                    //throw new ElementNotFoundException("Fatal Error while creating Element, in line" + i);
                    System.out.println("### Error in line " + i + ": " + e.getMessage());
                    e.printStackTrace();
                }

            } else if(text[i].contains("\\end{document}")) {
                break;
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
     *
     * @param currentElement last Element that was created
     * @param index current index of the text
     * @return the New Created Text Element with parent and child hierachie
     */
    private Element scanLine(final Element currentElement, final int index) {

        // end Part of current
        if (currentElement != null && currentElement.getEndPart() != null && text[index].contains(currentElement.getEndPart())) {
            currentElement.scanElementTextForSubElements(text, index);
            return currentElement;

        } else {
            Element element = ElementConfig.initType(this.text[index], index);

            if (element != null) {
                if (currentElement == null) {
                    setParentChild(root, element);

                } else if (element.getLevel() > currentElement.getLevel()) {
                    lowerLevel(currentElement, index, element);

                } else if (element.getLevel() == currentElement.getLevel()) {
                    sameLevel(currentElement, index, element);

                } else {
                    higherLevel(currentElement, index, element);
                }
                return element;
            } else {
                return currentElement;
            }
        }
    }

    /**
     *
     * @param currentElement
     * @param index
     * @param element
     */
    private void lowerLevel(Element currentElement, int index, Element element) {
        currentElement.scanElementTextForSubElements(text, index);
        setParentChild(currentElement, element);
    }

    /**
     *
     * @param currentElement
     * @param index
     * @param element
     */
    private void sameLevel(Element currentElement, int index, Element element) {
        currentElement.scanElementTextForSubElements(text, index);
        Element parent = currentElement.getParentElement();

        if (parent == null) {
            root.addChild(element);
        } else {
            setParentChild(parent, element);
        }
    }

    /**
     * higher lvl, navigate tree struct up,
     * search for next best spot
     *
     * @param currentElement
     * @param index
     * @param element
     */
    private void higherLevel(Element currentElement, int index, Element element) {
        // prev subsection
        Element searchElem = currentElement;

        while (searchElem != null && searchElem.getLevel() >= element.getLevel()) {
            searchElem.scanElementTextForSubElements(text, index);
            searchElem = searchElem.getParentElement();
        }

        if (searchElem == null) {
            root.addChild(element);
        } else if (searchElem.getLevel() == element.getLevel()) {
            Element parent = searchElem.getParentElement();
            setParentChild(parent, element);
        } else {
            setParentChild(searchElem, element);
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
        parent.addChild(child);
    }
}
