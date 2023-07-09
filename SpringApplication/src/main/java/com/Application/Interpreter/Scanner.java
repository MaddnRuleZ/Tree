package main.java.com.Application.Interpreter;

import main.java.com.Application.Tree.Element;
import main.java.com.Application.Tree.elements.Parent;
import main.java.com.Application.Tree.elements.Root;
import main.java.com.Application.Tree.elements.parents.environments.Algorithm;
import main.java.com.Application.Tree.elements.parents.environments.Equation;
import main.java.com.Application.Tree.elements.parents.environments.Figure;
import main.java.com.Application.Tree.elements.parents.sectioning.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Scanner {
    private final Map<String, Class<?>> startPartsMap;
    private final String[] text;
    private final Parent root;

    public Scanner(String[] text) {
        startPartsMap = createPartsMap();
        root = new Root();
        this.text = text;
    }

    // return root of parsed Doc or Teil Doc, eigentlicher algo teil
    public Element parseDocument() {
        Element currElement = null;
        for (int i = 0; i < text.length; i++) {
            if (text[i].contains("\\")) {
                currElement = scanLine(currElement, i);
            }
        }

        // fill last one
        if (currElement != null) {
            currElement.generateTextFromIndices(text, text.length - 1);
        }

        return null;
    }

    /**
     * Note:
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
                // null Level
                if (currentElement == null) {
                    root.addChild(element);
                    element.setParent(root);

                    // lower level
                } else if (element.getLevel() > currentElement.getLevel()) {
                    currentElement.generateTextFromIndices(text, index);
                    setParentChild(currentElement, element);

                    // eq lvl
                } else if (element.getLevel() == currentElement.getLevel()) {
                    currentElement.generateTextFromIndices(text, index);
                    Element parent = currentElement.getParentElement();

                    if (parent == null) {
                        root.addChild(element);

                    } else {
                        setParentChild(parent, element);
                    }


                    // higher lvl, search for next best spot
                } else {
                    // prev subsection
                    Element searchElem = currentElement;

                    while (searchElem != null && searchElem.getLevel() > element.getLevel()) {
                        searchElem.generateTextFromIndices(text, index);
                        searchElem = searchElem.getParentElement();
                    }

                    if (searchElem == null) {
                        root.addChild(element);
                    } else {
                        Element parent = searchElem.getParentElement();
                        setParentChild(parent, element);
                    }
                }
                return element;
            } else {
                return currentElement;
            }
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
        // Iterate over the map entries and check if currentLine contains a start part
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

    /// Debug only, shitty code ik . . .
    public void debugThatShit() {
        for (Element element: root.getChildElements()) {
            System.out.println(spacing(0) + element.getStartPart());
            printText(element);
            System.out.println(element.getLevel());
        }
    }

    private void printText(Element element) {
        String[] list = element.getText();
        if (list == null) {
            System.out.println("Error, element" + element.getId());
            return;
        }
        for (String line: list) {
            System.out.println(line);
        }
    }


    private String spacing(int multipl) {
        String str = "";
        for (int i = 0; i < multipl; i++) {
            str += "    ";
        }
        return str;
    }

}
