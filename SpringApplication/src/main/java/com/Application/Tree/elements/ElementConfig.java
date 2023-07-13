package com.Application.Tree.elements;

import com.Application.Interpreter.Parser;
import com.Application.Interpreter.Scanner;
import com.Application.Tree.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    ALGORITHM("\\begin{algorithm}", "\\end{algorithm}", 9) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Environment(getStartPart(), getEndPart(), index, getLevel());
        }
    },

    FIGURE("\\begin{figure}", "\\end{figure}", 9) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Environment(getStartPart(), getEndPart(), index, getLevel());
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
            return new Environment(getStartPart(), null, index, getLevel());
        }
    },

    CAPTION("\\caption", null, 10) {
        @Override
        Element getElement(int index, String currentLine) {
            return new Environment(getStartPart(), null, index, getLevel());
        }
    },

    INPUT("\\input", null, 11) {
        @Override
        Element getElement(int index, String currentLine) {
            String path;
            String regexPattern = "\\\\input\\{([^}]*)\\}";
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(currentLine);
            if (matcher.find()) {
                path = matcher.group(1);
            } else {
                System.out.println("Error, couldn't parse the path");
                return null;
            }

            Parser scanner = new Parser(path);
            return scanner.startParsing();
        }
    };

    private final String startPart;
    private final String endPart;
    private final int level;

    ElementConfig(String startPart, String endPart, int level) {
        this.level = level;
        this.startPart = startPart;
        this.endPart = endPart;
    }

    abstract Element getElement(int index, String currentLine);

    public static Element initType(String startPartLine, int index) {
        for (final ElementConfig sectioning: ElementConfig.values()) {
            if (startPartLine.contains(sectioning.startPart)) {
                return sectioning.getElement(index, startPartLine);
            }
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
