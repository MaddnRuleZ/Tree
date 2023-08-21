package com.application.tree.elements.parent;

import com.application.exceptions.UnknownElementException;
import com.application.interpreter.TextFileReader;
import com.application.tree.Element;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static int DEFAULT_LEVEL = 9;
    private String header = "";

    /**
     * Constructor for creating a new Environment object with the specified startPart, endPart, and level.
     *
     * @param startPart The startPart string that identifies the beginning of the environment container.
     * @param endPart   The endPart string that identifies the end of the environment container.
     * @param level     The hierarchical level of the environment container.
     */
    public Environment(String inputLine, String startPart, String endPart, int level) {
        super(startPart, endPart, level);
        setHeader(inputLine);
    }

    private void setHeader(String inputLine) {
        String regex = "\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputLine);

        if (matcher.find()) {
            this.header = matcher.group(1);
        }
    }

    /**
     * Add the Text of the Environment as content to the Element,
     * remove all leading Space Characters
     *
     * @param line current Line in the Text
     * @return the current Environment
     */
    public Element addTextBlockToElem(String line) {
        if (!summary.extractContent(line) && !comment.extractContent(line)) {
            line = TextFileReader.removeSpacesFromStart(line);
            this.textBuilder.append(line).append("\n");
            this.content = textBuilder.toString();
        }
        return this;
    }

    @Override
    public void toLaTeX(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        this.toLaTeXStart(map, key, level, exportComment, exportSummary);

        String indentationHeader = getIndentation(level);
        String indentationBody = getIndentation(level + 1);
        StringBuilder text = map.get(key);

        text.append(indentationHeader).append("\\begin");
        text.append("{").append(this.header).append("}").append("\n");
        if(this.options != null) {
            text.append("[").append(this.options).append("]");
        }
        for(String line: this.content.split("\n")) {
            text.append(indentationBody).append(line);
            text.append("\n");
        }

        text.append(indentationHeader).append("\\end");
        text.append("{").append(this.header).append("}");
        text.append("\n");

        this.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }

    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXStart(map, key, level, exportComment, exportSummary);
    }


    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }
}
