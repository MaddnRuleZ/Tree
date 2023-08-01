package com.application.interpreter;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.*;
import com.application.tree.elements.roots.Input;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;

/**
 * Scanner Class for Disassemble a Document into its Latex Structure Elements
 *
 */
public class Scanner {
    private final String[] text;
    private final Roots root;

    /**
     * Create a new Instance of the Scanner for Scanning a Text on Elements
     * Difference between the First Document Root and every following Document root
     *
     * @param text Text the Scanner shall Scan
     */
    public Scanner(String[] text) {
        if (Root.getInstance().getChildren().size() == 0) {
            root = Root.getInstance();
        } else {
            root = new Input();
        }
        this.text = text;
    }

    /**
     * Parse the Document by scanning the document line by line
     * if the line contains a new Element, created it or
     * Add the text to the current last TextBlockElement
     *
     * @return root Element of tree with rest of tree as Children
     */
    public Roots parseDocument() {
        Element lastElement = null;
        Element newElement = null;

        for (int i = 0; i < text.length; i++) {
            String line = text[i];
            if (line.contains(Root.START_DOCUMENT)) {
                Root.getInstance().addStartHeader(TextFileReader.extractStrings(text, 0, i));
                continue;
            }
            newElement = scanCurrentLine(lastElement, line);

            if (newElement != null) {
                lastElement = newElement;
            } else if (lastElement != null) {
                lastElement = lastElement.addTextBlockToElem(text[i]);
            }
        }
        return root;
    }

    /**
     * Scan the line for new Structure Element
     * or end the current LastElement (Environment only)
     * if found, create it add it relative to the last Element and return it; else return null
     *
     * @param lastElement last Element that was used
     * @param currentLine current Line to scan in the Text
     * @return the New Created Text Element with parent and child hierarchy
     */
    private Element scanCurrentLine(Element lastElement, String currentLine) {
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
                    setInputRootParentChildHierarchy(newElement);
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
     * newElement is in the Hierarchy higher Up, so
     * navigate tree up, until Element can be added based on it's level
     * that set Parent/Child status
     *
     * @param lastElement lastElement created, already in the Tree
     * @param newElement currentElement created, to add to Tree
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
     * set a double linked Connection between two Elements
     * set the Parent as Parent for the Child,
     * set the Child as Child for the Parent,
     *
     * @param parentElement Parent Element
     * @param child Child Element
     */
    private void setParentChild(Element parentElement, Element child) {
        Parent parent = (Parent) parentElement;
        if (parent != null) {
            child.setParent(parent);
            parent.addChild(child);
        }
    }

    /**
     * In case Root is instance of Input, also set the Elements Parent as the Root
     *
     * @param newElement
     * @throws UnknownElementException
     */
    private void setInputRootParentChildHierarchy(Element newElement) {
        try {
            if (newElement.getStartPart().equals(ElementConfig.INPUT.getStartPart())) {
                Input inputRoot = (Input) root;
                newElement.setParent(inputRoot);
            }
        } catch (UnknownElementException ignored) {
        }
    }
}
