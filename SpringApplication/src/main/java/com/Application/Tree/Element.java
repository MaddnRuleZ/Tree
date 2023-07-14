package com.Application.Tree;

import com.Application.Interpreter.TextFileReader;
import com.Application.Tree.additionalInfo.Comment;
import com.Application.Tree.additionalInfo.TextBlock;
import com.Application.Tree.additionalInfo.Summary;
import com.Application.Tree.elements.Parent;
import com.Application.Tree.interfaces.Exportable;
import com.Application.Tree.interfaces.JsonParser;
import java.util.*;

public abstract class Element implements JsonParser, Exportable {
    private final UUID id;
    private final int level;
    private Parent parentElement;
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
        this.text = null;

        comment = new Comment();
        summary = new Summary();
        textBlock = new TextBlock();
    }

    /**
     * ture if Element has no Text or can be overwritten (Dead Zone EdgeCase)
     *
     * @return
     */
    public abstract boolean validateIndicTextGeneration();

    /**
     * Read the contents of the structure Element
     * mini Parser For Comments, Summaries and NewLines, okay by now resorted to c,s,p. Max: nC, 1S nP
     *
     * text uselsess after this transformation, text is only in the newline characters
     *
     * @param text
     * @param endIndex
     */
    public void scanElementTextForSubElements(String[] text, int endIndex) {
        if (endIndex != 0 && validateIndicTextGeneration()) {
            String[] elementFullText = TextFileReader.extractStrings(text, this.startIndex + 1, endIndex - 1);
            List<String> remainingText = Arrays.stream(elementFullText).toList();
            this.options = extractOptionsString(text[this.startIndex]);
            this.text = elementFullText;

            remainingText = summary.extractInfo(remainingText);
            remainingText = comment.extractInfo(remainingText);
            textBlock.extractInfo(remainingText);
        }
    }

    /**
     * searches for the element with the given id
     * @param id to search for
     * @param level, num of passed sections from root to currentElement, only relevant if found id is (parent of) sectioning
     * @return found Element or null
     */
    public abstract Element searchForID(UUID id, int level);

    protected String extractOptionsString(String rawOptions) {
        return rawOptions;
    }
    public void setParent(Parent parentElement) {
        this.parentElement = parentElement;
    }
    public Parent getParentElement() {
        return parentElement;
    }
    public String getStartPart() {
        return startPart;
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
    public abstract boolean addChild(Element child);
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
}
