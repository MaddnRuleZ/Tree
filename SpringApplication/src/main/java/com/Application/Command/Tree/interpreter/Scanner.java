package interpreter;

import Tree.Element;
import Tree.elements.Parent;
import Tree.elements.Root;
import Tree.elements.childs.Equation;
import Tree.elements.parents.Paragraph;
import Tree.elements.parents.Part;
import Tree.elements.parents.Section;

public class Scanner {

    private final Parent root;
    private final String[] text;

    public Scanner(String[] text) {
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

            // end Part of Parent -> Label usw...
        } else if (currentElement != null && currentElement.getParentElement() != null
                && currentElement.getParentElement().getEndPart() != null
                && text[index].contains(currentElement.getParentElement().getEndPart())) {

            currentElement.getParentElement().generateTextFromIndices(text, index);
            currentElement.generateTextFromIndices(text, index);
            return currentElement.getParentElement();

            // new start Point
        } else {
            Element element = createNewElement(this.text[index], index);

            if (element != null) {
                // null Level
                if (currentElement == null) {
                    root.addChild(element);
                    element.setParent(root);
                    return element;

                    // lower level
                } else if (element.getLevel() > currentElement.getLevel()) {
                    currentElement.generateTextFromIndices(text, index);
                    setParentChild(currentElement, element);
                    return element;

                    // eq lvl
                } else if (element.getLevel() == currentElement.getLevel()) {
                    currentElement.generateTextFromIndices(text, index);
                    Element parent = currentElement.getParentElement();

                    if (parent == null) {
                        root.addChild(element);
                        return element;

                    } else {
                        setParentChild(parent, element);
                        return element;
                    }

                    // higher lvl, search for next best spot
                } else {
                    // prev subsection
                    Element searchElem = currentElement;
                    while (searchElem != null && searchElem.getLevel() >= element.getLevel()) {
                        searchElem.generateTextFromIndices(text, index);
                        searchElem = searchElem.getParentElement();
                    }

                    if (searchElem == null) {
                        root.addChild(element);

                    } else if (searchElem.getParentElement() == null) {
                        setParentChild(searchElem, element);
                    } else {
                        searchElem.generateTextFromIndices(text, index);
                        setParentChild(searchElem, element);
                    }
                    return element;
                }
            } else {
                return currentElement;
            }
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

    private Element createNewElement(String currentLine, int index) {
        if (currentLine.contains(Section.startPart)) {
            return new Section(index);
        } else if (currentLine.contains(Paragraph.startPart)) {
            return new Paragraph(index);
        } else if (currentLine.contains(Equation.startPart)) {
            return new Equation(index);
        } else if (currentLine.contains(Part.startPart)) {
            return new Part(index);
        }

        System.out.println("nicht erkanntes element line " + index);
        return null;
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

    /*
    private Element createNewElement(String currentLine, int index) {
        Element[] elements = {
                new Part(index),
                new Chapter(index),
                new Section(index),
                new SubSection(index),
                new SubSubSection(index),
                new Paragraph(index),
                new SubParagraph(index),
                new Figure(index),
                new Label(index),
                new IncludeGraphics(index),
                new Caption(index),
                new Verbatim(index)
        };

        for (Element element : elements) {
            if (currentLine.contains(element.getStartPart())) {
                return element;
            }
        }

        System.out.println("### Error start on creation of Element" + currentLine + index);
        return null;
    }
    */
    /*
    // Move to Element Top Class
    private Element createNewElement(String currentLine, int index) {

        if (currentLine.contains(Part.startPart)) {
            return new Part(index);
        } else if (currentLine.contains(Chapter.startPart)) {
            return new Chapter(index);
        }  else if (currentLine.contains(Section.startPart)) {
            return new Section(index);
        } else if (currentLine.contains(SubSection.startPart)) {
            return new SubSection(index);
        } else if (currentLine.contains(SubSubSection.startPart)) {
            return new SubSubSection(index);
        } else if (currentLine.contains(Paragraph.startPart)) {
            return new Paragraph(index);
        } else if (currentLine.contains(SubParagraph.startPart)) {
            return new SubParagraph(index);
        } else if (currentLine.contains(Figure.startPart)) {
            return new Figure(index);
        } else if (currentLine.contains(Label.startPart)) {
            return new Label(index);
        } else if (currentLine.contains(IncludeGraphics.startPart)) {
            return new IncludeGraphics(index);
        } else if (currentLine.contains(Caption.startPart)) {
            return new Caption(index);
        } else if (currentLine.contains(Verbatim.startPart)) {
            return new Verbatim(index);
        } else if (currentLine.contains(LstListings.startPart)) {
            return new LstListings(index);
        } else if (currentLine.contains(Algorithm.startPart)) {
            return new Algorithm(index);
        } else if (currentLine.contains(Equation.startPart)) {
            return new Equation(index);
        } else if (currentLine.contains(Equation2.startPart)) {
            return new Equation2(index);
        } else if (currentLine.contains(Equation3.startPart)) {
            return new Equation3(index);
        }

        System.out.println("### Error start on creation of Element" + currentLine + index);
        return null;
    }
    */



    private String spacing(int multipl) {
        String str = "";
        for (int i = 0; i < multipl; i++) {
            str += "    ";
        }
        return str;
    }

}
