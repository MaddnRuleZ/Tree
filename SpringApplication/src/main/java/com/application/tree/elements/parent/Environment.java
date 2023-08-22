package com.application.tree.elements.parent;

import com.application.exceptions.UnknownElementException;
import com.application.interpreter.TextFileReader;
import com.application.tree.Element;

import java.util.Map;

/**
 * Environment Class
 *
 * The Environment class represents a parent container that holds elements within a program.
 *
 * An Environment typically groups and organizes various child elements within a specific context.
 * It is defined by its startPart and endPart strings, which are used to identify the beginning and
 * end of the environment container within the program. The hierarchical level (level) represents the
 * nesting depth of this environment in relation to other elements.
 */
public class Environment extends Parent {
    public static String DEFAULT_OPENING = "\\begin";
    public static String DEFAULT_ENDING = "\\end";
    private final String header;

    /**
     * Constructor for creating a new Environment object with the specified startPart, endPart, and level.
     *
     * @param startPart The startPart string that identifies the beginning of the environment container.
     * @param endPart   The endPart string that identifies the end of the environment container.
     * @param level     The hierarchical level of the environment container.
     */
    public Environment(String inputLine, String startPart, String endPart, int level) {
        super(startPart, endPart, level);
        this.header = extractContent(inputLine);
    }

    /**
     * Add text to the Environment,
     * check if the line is summary or comment, if so extract the line
     * else assign the text to the Environment's content Variable
     *
     * @param line current Line in the Text
     * @return the current Environment
     */
    public Element addTextBlockToElem(String line) {
        if (!summary.extractSummary(line) && !comment.extractComment(line) && !line.contains(DEFAULT_ENDING)) {
            this.textBuilder.append(line).append("\n");
            this.content = textBuilder.toString();
        }
        return this;
    }

    @Override
    public void toLaTeX(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        this.toLaTeXStart(map, key, level, exportComment, exportSummary);
        String indentationBody = getIndentation(level + 1);
        StringBuilder text = map.get(key);

        if (!this.content.isEmpty()) {
            for (String line : this.content.split("\n")) {
                text.append(indentationBody).append(line);
                text.append("\n");
            }
        }
        this.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }

    /**
     * Export the comment of the Environment;
     * adds the \begin{header} and the options
     * @param map           map of the LaTeX-Code
     * @param key           key of the map
     * @param level         level of indentation
     * @param exportComment if comments should be exported
     * @param exportSummary if summary should be exported
     * @throws UnknownElementException
     */
    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXStart(map, key, level, exportComment, exportSummary);
        String indentation = getIndentation(level);
        StringBuilder text = map.get(key);

        text.append(indentation).append("\\begin");
        text.append("{").append(this.header).append("}");
        if (this.options != null) {
            text.append("[").append(this.options).append("]");
        }
        text.append("\n");
    }


    /**
     * Export the newline and summary of the Environment;
     * adds the \end{header}
     * @param map           map of the LaTeX-Code
     * @param key           key of the map
     * @param level         level of indentation
     * @param exportComment if comments should be exported
     * @param exportSummary if summary should be exported
     * @throws UnknownElementException
     */
    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        String indentation = getIndentation(level);
        StringBuilder text = map.get(key);

        text.append(indentation).append("\\end");
        text.append("{").append(this.header).append("}");
        text.append("\n");

        super.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }

    public String getHeader() {
        return header;
    }
}
