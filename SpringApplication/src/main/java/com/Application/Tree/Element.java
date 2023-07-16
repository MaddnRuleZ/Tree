package com.Application.Tree;

import com.Application.Interpreter.TextFileReader;
import com.Application.Interpreter.additionalInfo.Comment;
import com.Application.Interpreter.additionalInfo.TextBlock;
import com.Application.Interpreter.additionalInfo.Summary;
import com.Application.Tree.elements.Parent;
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
    protected String[] text;
    protected final Summary summary;
    protected final Comment comment;
    protected final TextBlock textBlock;
    private boolean chooseManualSummary;
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

        comment = new Comment();
        summary = new Summary();
        textBlock = new TextBlock();
    }

    public void setOptions(String optionsString) {
        this.options = optionsString;
    }
    public abstract String[] toText();

    // move to Child/ BlockElement
    public Element assignTextToBlock(String[] text, int endIndex) {
        //? wird nur auf Blocks gecalled?
        String[] elementFullText = TextFileReader.extractStrings(text, this.startIndex, endIndex - 1);
        this.text = elementFullText;
        return parentElement;
    }


    /**
     * searches for the element with the given id
     *
     * @param id to search for
     * @return found Element or null
     */
    public abstract Element searchForID(UUID id);

    protected String extractOptionsString(String rawOptions) {
        return rawOptions;
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
    public String[] getText() {
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
    public void setComment(String comment) {
        //TODO
    }

    public void setContent(String content) {
        //TODO
    }

    public void setSummary(String summary) {
        //TODO
    }

    public String getStartPart() {
        return this.startPart;
    }
}
