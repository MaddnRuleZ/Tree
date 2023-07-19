package com.Application.Tree;

import com.Application.Interpreter.TextFileReader;
import com.Application.Tree.additionalInfo.Comment;
import com.Application.Tree.additionalInfo.NewLine;
import com.Application.Tree.additionalInfo.Summary;
import com.Application.Tree.elements.Parent;
import com.Application.Tree.elements.Root;
import com.Application.Tree.interfaces.Exportable;
import com.Application.Tree.interfaces.JsonParser;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 */
public abstract class Element implements JsonParser, Exportable {
    private final UUID id;
    private final int level;
    private Element parentElement;

    protected List<String> text;
    protected final Summary summary;
    protected final Comment comment;
    protected final NewLine newLine;
    private boolean chooseManualSummary;
    protected String content;
    protected String options;
    protected final int startIndex;
    private final String startPart;
    private final String endPart;

    public Element(String startPart, String endPart, int startIndex, int level) {
        this.id = UUID.randomUUID();
        this.startPart = startPart;
        this.endPart = endPart;
        this.startIndex = startIndex;
        this.level = level;
        this.text = new ArrayList<>();

        comment = new Comment();
        summary = new Summary();
        newLine = new NewLine();
        Root.updateLevelCap(level);
    }


    public abstract List<String> toText();

    public Element assignTextToTextBlock(String[] text, int endIndex) {
        // TODO
        this.text = TextFileReader.extractStrings(text, this.startIndex, endIndex);
        return parentElement;
    }

    public void setParent(Element parentElement) {
        this.parentElement = parentElement;
    }
    public Parent getParentElement() {
        return (Parent) parentElement;
    }
    public String getEndPart() {
        return endPart;
    }
    public String toJson() {
        return null;
    }
    public List<String> getText() {
        return this.text;
    }
    public UUID getId() {
        return this.id;
    }
    public int getLevel() {
        return level;
    }
    public String getOptions() {
        return this.options;
    }
    public boolean isChooseManualSummary() {
        return chooseManualSummary;
    }
    public void setChooseManualSummary(boolean chooseManualSummary) {
        this.chooseManualSummary = chooseManualSummary;
    }

    public void setOptions(String optionsString) {
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(optionsString);

        if (matcher.find()) {
            this.options = matcher.group(1);
        } else {
            this.options = null;
        }
    }

    public void setContent(String content) {
        Pattern pattern = Pattern.compile("\\[([^]]*)\\]");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            this.content = matcher.group(1);
        } else {
            this.content = null;
        }
    }

    public void setComment(String comment) {
        //TODO
    }

    public void setSummary(String summary) {
        //TODO
    }

    /**
     * calculates the level of the calling Element from bottom to top
     * @return level of the calling Element
     */
    public int calculateLevelFromElement() {
        return this.getParentElement().calculateLevelFromElement() + 1;
    }

    /**
     * searches for the element with the given id
     * @param id to search for
     * @return found Element or null
     */
    public abstract Element searchForID(UUID id);

    /**
     * traverses the tree starting from calling Element
     * Passed Sections add 1 to the level
     * @return level of the deepest sectioning child
     */
    public abstract int levelOfDeepestSectioningChild();

    /**
     * Check if the current line is Summary Comment or NewLine
     * if so add the text to the Summary Comment or NewLine, else add the text to the BlockElement
     *
     * @param line line to Scan for Summary Comment or NewLine
     */
    public void addText(String line) {
        if (!summary.extractContent(line) && !comment.extractContent(line) && !newLine.extractContent(line) ) { //&& !summary.extractContent(line)
            this.text.add(line);
        }
    }
}
