package com.Application.Interpreter;

import com.Application.Tree.Element;
import com.Application.Tree.additionalInfo.NewLine;
import com.Application.Tree.elements.*;
import com.Application.Tree.interfaces.Roots;

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
     * todo wird noch geändert
     * scan the given Document for Latex Structure Elements and return the root Element for this Part of the Tree
     *
     * Err List:
     * first contains can be t/ false
     *
     * @return root Element of Tree
     */
    public Roots parseDocument() {
        Element currElement = null;

        for (int i = 0; i < text.length; i++) {
            Element newElement = scanLine(currElement, i);
            if (newElement != null) {
                currElement = newElement;

            } else {

                if (currElement instanceof Parent && ((Parent) currElement).getChildElements().size() == 0) {
                    BlockElement textBlockElement = new BlockElement(null, null, i);
                    setParentChild(currElement, textBlockElement);
                    currElement = textBlockElement;
                    currElement.addText(text[i]);

                } else if (currElement instanceof BlockElement && NewLine.checkLineForNewLineCharacters(text[i])) {
                    currElement.addText(text[i]);
                    BlockElement textBlockElement = new BlockElement(null, null, i + 1);
                    Parent parent = currElement.getParentElement();
                    setParentChild(parent, textBlockElement);
                    currElement = textBlockElement;

                } else if (currElement instanceof Environment) {
                    BlockElement textBlockElement = new BlockElement(null, null, i);
                    Parent parent = currElement.getParentElement();
                    setParentChild(parent, textBlockElement);
                    currElement = textBlockElement;
                    currElement.addText(text[i]);

                } else if (currElement instanceof Child && !(currElement instanceof BlockElement)) {
                    // label
                    BlockElement textBlockElement = new BlockElement(null, null, i);
                    Parent parent = currElement.getParentElement();
                    setParentChild(parent, textBlockElement);
                    currElement = textBlockElement;
                    currElement.addText(text[i]);

                }  else {
                    currElement.addText(text[i]);
                }
            }
        }
        return root;
    }

    /**
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
            Element newElement = ElementConfig.createElement(currentLine, index);

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
