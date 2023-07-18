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
    protected final NewLine textBlock;
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
        textBlock = new NewLine();
        Root.updateLevelCap(level);
    }

    public void setOptions(String optionsString) {
        // todo do RegeX
        this.options = optionsString;
    }

    public abstract List<String> toText();

    public Element assignTextToTextBlock(String[] text, int endIndex) {
        // todo assertion
        this.text = TextFileReader.extractStrings(text, this.startIndex, endIndex - 1);
        // extract the summ, comment, nl
        List<String> restText = comment.extractInfo(this.text);

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
    public boolean isTextBlock() {
        return this.startPart == null;
    }

    public void setComment(String comment) {
        //TODO
    }

    public void setContent(String content) {
        // todo do RegeX

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

    public void addText(String line) {
        this.text.add(line);
    }
}
