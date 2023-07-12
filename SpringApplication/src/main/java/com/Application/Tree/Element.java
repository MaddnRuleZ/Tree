package com.Application.Tree;

import com.Application.Interpreter.TextFileReader;
import com.Application.Tree.additionalInfo.Comment;
import com.Application.Tree.additionalInfo.NewLine;
import com.Application.Tree.additionalInfo.Summary;
import com.Application.Tree.elements.environments.Algorithm;
import com.Application.Tree.elements.environments.Equation;
import com.Application.Tree.elements.environments.Figure;
import com.Application.Tree.elements.sectioning.*;
import com.Application.Tree.interfaces.Exportable;
import com.Application.Tree.interfaces.JsonParser;
import java.util.*;

public abstract class Element implements JsonParser, Exportable {
    private static Map<Element, Integer> LEVEL_MAP;
    private final UUID id;
    private int level;
    private Element parentElement;
    protected String[] text;
    protected final Summary summary;
    protected final Comment comment;
    protected final NewLine newLine;
    private boolean chooseManualSummary;
    private String options;
    private final int startIndex;
    private final String startPart;
    private final String endPart;

    public Element(String startPart, String endPart, int startIndex) {
        this.id = UUID.randomUUID();
        this.startPart = startPart;
        this.endPart = endPart;
        this.startIndex = startIndex;
        this.text = null;

        comment = new Comment();
        summary = new Summary();
        newLine = new NewLine();
        setLevel(startPart);
    }

    public static void createLevelMap() {
        LEVEL_MAP = new HashMap<>();
        LEVEL_MAP.put(new Root(), 0);

        LEVEL_MAP.put(new Part(-1), 1);
        LEVEL_MAP.put(new Chapter(-1), 2);
        LEVEL_MAP.put(new Section(-1), 3);
        LEVEL_MAP.put(new SubSection(-1), 4);
        LEVEL_MAP.put(new SubSubSection(-1), 5);
        LEVEL_MAP.put(new Paragraph(-1), 6);
        LEVEL_MAP.put(new SubParagraph(-1), 7);

        LEVEL_MAP.put(new Equation(-1), 9);
        LEVEL_MAP.put(new Algorithm(-1), 9);
        LEVEL_MAP.put(new Figure(-1), 9);
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
     * @param text
     * @param endIndex
     */
    public void generateTextFromIndices(String[] text, int endIndex) {
        if (endIndex != 0 && validateIndicTextGeneration()) {
            String[] elementFullText = TextFileReader.extractStrings(text, this.startIndex +1, endIndex -1); // +1; -1 for removal of overlapping
            List<String> remainingText = Arrays.stream(elementFullText).toList();
            this.options = extractOptionsString(text[this.startIndex]);
            this.text = elementFullText;

            remainingText = summary.extractInfo(remainingText);
            remainingText = comment.extractInfo(remainingText);
            newLine.extractInfo(remainingText);
        }
    }

    private void setLevel(String startPart) {
        for (Map.Entry<Element ,Integer> entry: LEVEL_MAP.entrySet()) {
            Element entryElement = entry.getKey();
            Integer level = entry.getValue();

            if (entryElement.getStartPart().equals(startPart)) {
                this.level = level;
            }
        }
    }

    private String extractOptionsString(String rawOptions) {
        // do Regex to extract the Options String Parts
        return rawOptions;
    }

    public void setParent(Element parentElement) {
        this.parentElement = parentElement;
    }
    public Element getParentElement() {
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
}
