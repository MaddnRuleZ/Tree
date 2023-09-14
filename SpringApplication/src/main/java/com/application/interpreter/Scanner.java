package com.application.interpreter;

import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.elements.childs.BlockElement;
import com.application.tree.elements.parent.Environment;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Input;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;

/**
 * Scanner Class for Disassembling a Document into its Latex Structure Elements
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
     * Parse the Document by scanning the document line by line Testing PPS only
     * if the line contains a new Element, created it or
     * Add the text to the current last TextBlockElement
     *
     * @return root Element of tree with rest of tree as Children
     */
    public Roots parseDocument() throws ParseException {
        Element lastElement = null;
        Element newElement;

        for (int i = 0; i < text.length; i++) {
            String line = text[i];

            if (line.contains(Root.START_DOCUMENT)) {
                Root.getInstance().addStartHeader(TextFileReader.extractStrings(text, 0, i));
                continue;
            } else if (line.contains(Root.END_DOCUMENT)) {
                break;
            } else if (line.contains(Input.START_PART) && Root.getInstance().startHeaderExists()) {
                continue;
            }

            newElement = scanCurrentLine(lastElement, line);
            if (newElement == null && lastElement instanceof Environment && line.contains(Environment.DEFAULT_ENDING)) {
                lastElement = null;
                continue;
            }

            if (newElement != null) {
                lastElement = newElement;
            } else if (lastElement != null) {
                lastElement = lastElement.addTextBlockToElem(line);
            } else if (!Root.getInstance().startHeaderExists()) {
                lastElement = addStartTextBlock(line);
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
    private Element scanCurrentLine(Element lastElement, String currentLine) throws ParseException {
        if (checkLineEndsEnvironment(lastElement, currentLine)) {
            lastElement.addTextBlockToElem(getSubstringBeforeEnd(currentLine));
            // End current Environment
            return lastElement.getParentElement();

        } else if ((lastElement != null && lastElement.getEndPart() != null && lastElement.getEndPart().equals(Environment.DEFAULT_ENDING)) || lastElement != null && lastElement.getSummary().isListeningOnDocument()) {
            // Don't Parse Custom Environments
            return null;

        } else {
            Element newElement = ElementConfig.createElement(currentLine);
            if (newElement == null) {
                return null;
            }

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
        }
    }

    /**
     * Add a TextBlock to the DocumentRoot
     * In case Document starts with an TextBlock, add one to the Document Root
     *
     * @param line textLine to add
     * @return return added TextBlock
     */
    private Element addStartTextBlock(String line) {
        Element lastElement;
        BlockElement blockElement = new BlockElement();
        blockElement.addTextBlockToElem(line);
        root.addChild(blockElement);
        lastElement =  blockElement;
        return lastElement;
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
     * Check if the currentLine ends the lastElement or the currentLine ends the lastElement's Parent
     * check if the line ends the current Environment,
     * in case Figure the parent.Element will match the Endpart,
     * any other the lastElement will match the Endpart
     *
     * @param lastElement last Element use
     * @param currentLine current Line in the Text being scanned
     * @return true if lastElement or LastElements parent contains the EndPart
     */
    private boolean checkLineEndsEnvironment(Element lastElement, String currentLine) {
        return (lastElement != null && lastElement.getEndPart() != null && currentLine.contains(lastElement.getEndPart())
                || lastElement != null && lastElement.getParentElement() != null &&  lastElement.getParentElement().getEndPart() != null
                && currentLine.contains(lastElement.getParentElement().getEndPart()));
    }


    public String getSubstringBeforeEnd(String input) {
        int endIndex = input.indexOf(Environment.DEFAULT_ENDING);
        if (endIndex != -1) {
            return TextFileReader.removeSpacesFromStart(input.substring(0, endIndex));
        } else {
            return input;
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
