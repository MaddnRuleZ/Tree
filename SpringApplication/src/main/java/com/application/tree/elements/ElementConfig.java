package com.application.tree.elements;

import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.interpreter.Parser;
import com.application.interpreter.TextFileReader;
import com.application.printer.Printer;
import com.application.tree.Element;
import com.application.tree.elements.childs.Child;
import com.application.tree.elements.parent.Environment;
import com.application.tree.elements.parent.Figure;
import com.application.tree.elements.parent.Sectioning;
import com.application.tree.elements.roots.Input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Containing the Configuration of ALL the Structural Elements that wil be detected by the Scan -Algorithm
 *
 * Elements are Sorted in the Tree in an SEMI-ORDER identified by the Element's Level
 * When comparing two Elements, how they are Related to Each other the Level decides which is Parent and which Child.
 * So the Tree gets Balanced:
 *
 * [will be sorted to ->]
 * \Part         \Part{}              [indentation represents here who is parent and child]
 * \Section          \Section{}
 * \Label                \Label{}
 * \Par          \Part{}
 */
public enum ElementConfig {

    /**
     * to add a new Structure Element form LaTeX to the Parser:
     * - Copy one of the following Enums, add the Start- and EndPart
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

    ALGORITHM("\\begin{algorithmic}", "\\end{algorithmic}", GET_ENVIRONMENT_DEFAULT_LEVEL()) {
        @Override
        Element getElement(String currentLine) {
            return new Environment(currentLine, getStartPart(), getEndPart(), getLevel());
        }
    },

    LSLISTINGS("\\begin{lstlistings}", "\\end{lstlistings}", GET_ENVIRONMENT_DEFAULT_LEVEL()) {
        @Override
        Element getElement(String currentLine) {
            return new Environment(currentLine,getStartPart(), getEndPart(), getLevel());
        }
    },

    VERBATIM("\\begin{verbatim}", "\\end{verbatim}", GET_ENVIRONMENT_DEFAULT_LEVEL()) {
        @Override
        Element getElement(String currentLine) {
            return new Environment(currentLine,getStartPart(), getEndPart(), getLevel());
        }
    },

    EQUATION("\\begin{equation}", "\\end{equation}", GET_ENVIRONMENT_DEFAULT_LEVEL()) {
        @Override
        Element getElement(String currentLine) {
            return new Environment(currentLine, getStartPart(), getEndPart(), getLevel());
        }
    },

    FIGURE("\\begin{figure}", "\\end{figure}", GET_ENVIRONMENT_DEFAULT_LEVEL()) {
        @Override
        Element getElement(String currentLine) {
            return new Figure(getStartPart(), getEndPart(), getLevel());
        }
    },

    LABEL("\\label", null, 10) {
        @Override
        Element getElement(String currentLine) {
            return new Child(getStartPart(),null , getLevel());
        }
    },

    /**
     * Document inside this Document detected, start a new Parser
     *
     * Level gets set to Max in Input Class
     */
    INPUT("\\input", null, GET_BLOCK_ELEMENT_LEVEL()) {
        @Override
        Element getElement(String currentLine) throws ParseException {
            Matcher matcher = Pattern.compile(Element.CONTENT_REGEX).matcher(currentLine);
            if (matcher.find()) {
                String path = matcher.group(1);
                path = ensureTexExtension(path);

                try {
                    Parser parser = new Parser(Printer.getDirectoryPath() + "/" + path);
                    return (Input) parser.startParsingText();

                } catch (FileInvalidException e) {
                    throw new ParseException(currentLine);
                }
            }
            throw new ParseException(currentLine);
        }
    };

    public static final int BLOCK_ELEMENT_LEVEL = 99999;
    private static final int ENVIRONMENT_DEFAULT_LEVEL = 9;
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
    abstract Element getElement(String currentLine) throws ParseException;

    public static Element createElement(String startPartLine) throws ParseException {
        String additionalText = getSubstringAfterLastClosingBraceOrBracket(startPartLine);
        additionalText = TextFileReader.removeSpacesFromStart(additionalText);

        for (final ElementConfig sectioning: ElementConfig.values()) {
            if (startPartLine.contains(sectioning.startPart)) {
                Element createdElement = sectioning.getElement(startPartLine);
                createdElement.setContent(startPartLine);
                createdElement.setOptions(startPartLine);
                createdElement.addTextBlockToElem(additionalText);
                return createdElement;
            }
        }

        if (startPartLine.contains(Environment.DEFAULT_OPENING)) {
            Element createdElement = new Environment(startPartLine, Environment.DEFAULT_OPENING, Environment.DEFAULT_ENDING, ENVIRONMENT_DEFAULT_LEVEL);
            createdElement.setContent(startPartLine);
            createdElement.setOptions(startPartLine);
            createdElement.addTextBlockToElem(additionalText);
            return createdElement;
        }
        return null;
    }

    /**
     * Get the ElementConfig of a Sectioning Element based on its Level
     * @param level Level of the searched Sectioning Element
     * @return ElementConfig of the Sectioning Element
     */
    public static ElementConfig getSectioningType(int level) {
        if (level == ElementConfig.PART.level) {
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

    /**
     * Returns the substring after the last '}' or ']' character, based on which comes later in the string.
     * Adds a newline character '\n' in case the argument is on one line.
     *
     * @param input The input string
     * @return The substring after the last closing brace '}' or ']', with added newline if needed
     */
    public static String getSubstringAfterLastClosingBraceOrBracket(String input) {
        int lastIndexBrace = input.lastIndexOf('}');
        int lastIndexBracket = input.lastIndexOf(']');
        int lastIndex = Math.max(lastIndexBrace, lastIndexBracket);
        if (lastIndex + 1 < input.length()) {
            return input.substring(lastIndex + 1);
        }
        return "";
    }
    public static String ensureTexExtension(String filename) {
        if (filename.endsWith(".tex")) {
            return filename;
        } else {
            return filename + ".tex";
        }
    }



    public static int GET_ENVIRONMENT_DEFAULT_LEVEL() {
        return ENVIRONMENT_DEFAULT_LEVEL;
    }

    public static int GET_BLOCK_ELEMENT_LEVEL() {
        return BLOCK_ELEMENT_LEVEL;
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
