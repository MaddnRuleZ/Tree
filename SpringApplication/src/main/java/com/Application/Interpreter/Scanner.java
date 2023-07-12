package com.Application.Interpreter;

import com.Application.Tree.Element;
import com.Application.Tree.elements.sectioning.*;
import com.Application.Tree.elements.environments.Algorithm;
import com.Application.Tree.elements.environments.Equation;
import com.Application.Tree.elements.environments.Figure;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Scanner {
    private final Map<String, Class<?>> startPartsMap;
    private final String[] text;
    private final Root root;

    /**
     *
     * @param text
     */
    public Scanner(String[] text) {
        startPartsMap = createPartsMap();
        root = new Root();
        this.text = text;
    }

    // return root of parsed Doc or Teil Doc, eigentlicher algo teil

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
                    // fatal Err in Elem generation
                    System.out.println("### Error in line " + i + ": " + e.getMessage());
                    e.printStackTrace();
                }
            } else if(text[i].contains("\\end{document}")) {
                break;
            }

            if (i == 218) {
                System.out.println("### debugging Index reached, debugging purpose only");

            }
        }

        // fill last one
        if (currElement != null) {
            currElement.generateTextFromIndices(text, text.length - 1);
        }

        return root;
    }

    /**
     * Note: (Outdated doc)
     * Elemente sind in hierachie angeordent, in einer Halbordung, also
     * part, chapter .... subparagraph
     * basierend darauf werden Elemente als Kind oder an den Parent angehangen um die verschiedenen Ebenen (level) zu navigieren
     *
     * Documentation:
     * check if new end or starting point,
     * new : create new Element, compare level of current Element and new created element
     *       based on this add element to a parent or add element as child
     *       (some edge cases)
     *       when current level is null level
     *
     * @param currentElement last Element that was created
     * @param index current index of the text
     * @return the New Created Text Element with parent and child hierachie
     */
    private Element scanLine(final Element currentElement, final int index) {

        // end Part of current
        if (currentElement != null && currentElement.getEndPart() != null && text[index].contains(currentElement.getEndPart())) {
            currentElement.generateTextFromIndices(text, index);
            return currentElement;

        } else {

            Element element = createNewElement(this.text[index], index);
            if (element != null) {

                if (currentElement == null) {
                    // root Level
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
        currentElement.generateTextFromIndices(text, index);
        setParentChild(currentElement, element);
    }

    /**
     *
     * @param currentElement
     * @param index
     * @param element
     */
    private void sameLevel(Element currentElement, int index, Element element) {
        currentElement.generateTextFromIndices(text, index);
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

        // ERR
        while (searchElem != null && searchElem.getLevel() >= element.getLevel()) {
            searchElem.generateTextFromIndices(text, index);
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
     * init PartMap for easier decition on what to generate
     *
     * @return
     */
    private Map<String, Class<?>>  createPartsMap() {
        Map<String, Class<?>> partsMap = new HashMap<>();
        partsMap = new HashMap<>();
        partsMap.put(Part.startPart, Part.class);
        partsMap.put(Chapter.startPart, Chapter.class);
        partsMap.put(Section.startPart, Section.class);
        partsMap.put(SubSection.startPart, SubSection.class);
        partsMap.put(SubSubSection.startPart, SubSubSection.class);
        partsMap.put(Paragraph.startPart, Paragraph.class);
        partsMap.put(SubParagraph.startPart, SubParagraph.class);

        partsMap.put(Figure.startPart, Figure.class);
        partsMap.put(Equation.startPart, Equation.class);
        partsMap.put(Algorithm.startPart, Algorithm.class);

        return partsMap;
    }

    /**
     * Create an new Element based on the startPart map
     *
     * @param currentLine
     * @param index
     * @return
     */
    private Element createNewElement(String currentLine, int index) {
        for (Map.Entry<String, Class<?>> entry : this.startPartsMap.entrySet()) {
            String startPart = entry.getKey();
            Class<?> clazz = entry.getValue();

            if (currentLine.contains(startPart)) {
                try {
                    return (Element) clazz.getConstructor(int.class).newInstance(index);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
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

    // dont delete yet, for edges
    // Create a map to associate start parts with their corresponding classes
    /*
    // for labels usw in the following Context . . . .

      // end Part of Parent -> Label usw...
        } else if (currentElement != null && currentElement.getParentElement() != null
                && currentElement.getParentElement().getEndPart() != null
                && text[index].contains(currentElement.getParentElement().getEndPart())) {

            currentElement.getParentElement().generateTextFromIndices(text, index);
            currentElement.generateTextFromIndices(text, index);
            return currentElement.getParentElement();

            // new start Point
        }
     */


    /*
     else if (searchElem.getParentElement() == null) {
        setParentChild(searchElem, element);
        //?
    }
     */
}
