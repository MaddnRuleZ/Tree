package com.Application.Tree.elements;

import com.Application.Interpreter.Parser;
import com.Application.Tree.Element;
import com.Application.Tree.interfaces.Roots;

/**
 * Class Containing the Configuration of the Structural Elements that wil be detected by the Scan- Algorithm
 *
 *
 */
public enum ElementConfig {

    PART("\\part", null, 1) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Sectioning(getStartPart(), index, getLevel());
        }
    },

    CHAPTER("\\chapter", null, 2) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Sectioning(getStartPart(), index, getLevel());
        }
    },

    SECTION("\\section", null, 3) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Sectioning(getStartPart(), index, getLevel());
        }
    },

    SUBSECTION("\\subsection", null, 4) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Sectioning(getStartPart(), index, getLevel());
        }
    },

    SUBSUBSECTION("\\subsubsection", null, 5) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Sectioning(getStartPart(), index, getLevel());
        }
    },

    PARAGRAPH("\\paragraph", null, 6) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Sectioning(getStartPart(), index, getLevel());
        }
    },

    SUBPARAGRAPH("\\subparagraph", null, 7) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Sectioning(getStartPart(), index, getLevel());
        }
    },


    ALGORITHM("\\begin{algorithmic}", "\\end{algorithmic}", 9) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Environment(getStartPart(), getEndPart(), index, getLevel());
        }
    },

    LSLISTINGS("\\begin{lstlistings}", "\\end{lstlistings}", 9) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Environment(getStartPart(), getEndPart(), index, getLevel());
        }
    },

    VERBATIM("\\begin{verbatim}", "\\end{verbatim}", 9) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Environment(getStartPart(), getEndPart(), index, getLevel());
        }
    },

    FIGURE("\\begin{figure}", "\\end{figure}", 9) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Figure(getStartPart(), getEndPart(), index, getLevel());
        }
    },

    EQUATION("\\begin{equation}", "\\end{equation}", 9) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Environment(getStartPart(), getEndPart(), index, getLevel());
        }
    },

    LABEL("\\label", null, 10) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Child(getStartPart(),null , index, getLevel());
        }
    },

    INPUT("\\input", null, 11) {
        @Override
        Element getElement(int index, String currentLine) {
            String path = Input.extractPathRegex(currentLine);
            if (path == null) {
                // todo Err msg
                return null;
            }

            Parser parser = new Parser(path);
            Roots root = parser.startParsing();

           if (root instanceof Input) {
               return (Element) root;
           } else {
               System.out.println("Illegal Program State on Creation Of Element");
               return null;
           }
        }
    };

    private final String startPart;
    private final String endPart;
    private final int level;

    /**
     * Generate a New Element Configuration, for adding a new Detected Structure Element
     * Elements are sorted in a semi-order, based on their Level
     *
     * @param startPart startPart of the new Element
     * @param endPart endPart of the new Element, can be null
     * @param level nesting Level of the Element
     */
    ElementConfig(String startPart, String endPart, int level) {
        this.level = level;
        this.startPart = startPart;
        this.endPart = endPart;
    }

    /**
     * Enter the current Line and get the Element that is initiated in this Line
     *
     * @param index index of the current Line
     * @param currentLine current Text Line from Text, containing the Elements start Part
     * @return new Generated Element already initiated
     */
    abstract Element getElement(int index, String currentLine);

    /**
     * Create a new Element based on the line the Scanner read in the TextFile
     *
     * @param startPartLine textLine containing one of the startParts
     * @param index current Index in the Text
     * @return new Created Element
     */
    public static Element createElement(String startPartLine, int index) {
        for (final ElementConfig sectioning: ElementConfig.values()) {
            if (startPartLine.contains(sectioning.startPart)) {
                return sectioning.getElement(index, startPartLine);
            }
        }

        if (startPartLine.contains("\\begin")) {
            return new Environment("\\begin", "\\end", index, 9);
        }
        return null;
    }

    public String getStartPart() {
        return this.startPart;
    }

    public String getEndPart() {
        return this.endPart;
    }

    public int getLevel() {
        return this.level;
    }
}
