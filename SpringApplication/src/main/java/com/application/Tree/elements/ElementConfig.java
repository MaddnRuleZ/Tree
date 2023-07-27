package com.application.Tree.elements;

import com.application.Interpreter.Parser;
import com.application.Tree.Element;
import com.application.Tree.elements.childs.Child;
import com.application.Tree.elements.parent.Environment;
import com.application.Tree.elements.parent.Figure;
import com.application.Tree.elements.roots.Input;
import com.application.Tree.elements.parent.Sectioning;

/**
 * Class Containing the Configuration of ALL the Structural Elements that wil be detected by the Scan -Algorithm
 *
 */
public enum ElementConfig {

    /**
     * to add a new Structure Element form LaTeX to the Parser:
     * - Copy one of the following, add the Start- and Endpart
     * - Rename it
     * - set Level so Elements will get correctly sorted in Parent/Child Hierarchy
     * - change the returned Class type, see each Class for determining the correct functionality for your new Element
     */
    PART("\\part", null, 1) {
        @Override
        Element getElement(String currentLine) {
            return new Sectioning(getStartPart(), getLevel());
        }
    },

    CHAPTER("\\chapter", null, 2) {
        @Override
        Element getElement(String currentLine) {
            return new Sectioning(getStartPart(), getLevel());
        }
    },

    SECTION("\\section", null, 3) {
        @Override
        Element getElement(String currentLine) {
            return new Sectioning(getStartPart(), getLevel());
        }
    },

    SUBSECTION("\\subsection", null, 4) {
        @Override
        Element getElement(String currentLine) {
            return new Sectioning(getStartPart(), getLevel());
        }
    },

    SUBSUBSECTION("\\subsubsection", null, 5) {
        @Override
        Element getElement(String currentLine) {
            return new Sectioning(getStartPart(), getLevel());
        }
    },

    PARAGRAPH("\\paragraph", null, 6) {
        @Override
        Element getElement(String currentLine) {
            return new Sectioning(getStartPart(), getLevel());
        }
    },

    SUBPARAGRAPH("\\subparagraph", null, 7) {
        @Override
        Element getElement(String currentLine) {
            return new Sectioning(getStartPart(), getLevel());
        }
    },

    ALGORITHM("\\begin{algorithmic}", "\\end{algorithmic}", Environment.DEFAULT_LEVEL) {
        @Override
        Element getElement( String currentLine) {
            return new Environment(getStartPart(), getEndPart(), getLevel());
        }
    },

    LSLISTINGS("\\begin{lstlistings}", "\\end{lstlistings}", Environment.DEFAULT_LEVEL) {
        @Override
        Element getElement(String currentLine) {
            return new Environment(getStartPart(), getEndPart(), getLevel());
        }
    },

    VERBATIM("\\begin{verbatim}", "\\end{verbatim}", Environment.DEFAULT_LEVEL) {
        @Override
        Element getElement(String currentLine) {
            return new Environment(getStartPart(), getEndPart(), getLevel());
        }
    },

    FIGURE("\\begin{figure}", "\\end{figure}", Environment.DEFAULT_LEVEL) {
        @Override
        Element getElement(String currentLine) {
            return new Figure(getStartPart(), getEndPart(), getLevel());
        }
    },

    EQUATION("\\begin{equation}", "\\end{equation}", Environment.DEFAULT_LEVEL) {
        @Override
        Element getElement(String currentLine) {
            return new Environment(getStartPart(), getEndPart(), getLevel());
        }
    },

    LABEL("\\label", null, 10) {
        @Override
        Element getElement(String currentLine) {
            return new Child(getStartPart(),null , getLevel());
        }
    },

    /**
     * Document in Document detected, start a new Parser
     */
    INPUT("\\input", null, 0) {
        @Override
        Element getElement(String currentLine) {
            String path = Input.extractPathRegex(currentLine);
            Parser parser = new Parser(path);

            try {
                return (Input) parser.startParsing();
            } catch (ClassCastException e) {
                System.out.println("Input Document couldn't be detected");
                return null;
            }
        }
    };

    private final String startPart;
    private final String endPart;
    private final int level;

    /**
     * Constructor for saving different Types of Elements and assigning them a Level
     * Elements are sorted in a semi-order, based on their Level:
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
     * @param currentLine current Text Line from Text, containing the Elements start Part
     * @return new Generated Element already initiated
     */
    abstract Element getElement(String currentLine);

    /**
     * Create a new Element based on the line the Scanner read in the TextFile
     *
     * @param startPartLine textLine containing one of the startParts
     * @return new Created Element
     */
    public static Element createElement(String startPartLine) {
        for (final ElementConfig sectioning: ElementConfig.values()) {
            if (startPartLine.contains(sectioning.startPart)) {
                return sectioning.getElement(startPartLine);
            }
        }

        if (startPartLine.contains(Environment.DEFAULT_OPENING)) {
            return new Environment(Environment.DEFAULT_OPENING, Environment.DEFAULT_ENDING, Environment.DEFAULT_LEVEL);
        }
        return null;
    }

    /**
     * Get the ElementConfig of a Sectioning Element based on its Level
     * @param level Level of the searched Sectioning Element
     * @return ElementConfig of the Sectioning Element
     */
    public static ElementConfig getSectioningType(int level) {
        if(level == ElementConfig.PART.level) {
            return PART;
        } else if (level == ElementConfig.CHAPTER.level) {
            return CHAPTER;
        } else if (level == ElementConfig.SECTION.level) {
            return SECTION;
        } else if (level == ElementConfig.SUBSECTION.level) {
            return SUBSECTION;
        } else if (level == ElementConfig.SUBSUBSECTION.level) {
            return SUBSUBSECTION;
        } else if (level == ElementConfig.PARAGRAPH.level) {
            return PARAGRAPH;
        } else if (level == ElementConfig.SUBPARAGRAPH.level) {
            return SUBPARAGRAPH;
        } else {
            return null;
        }
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
