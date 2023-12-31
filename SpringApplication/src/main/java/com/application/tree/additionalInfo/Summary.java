package com.application.tree.additionalInfo;

import com.application.interpreter.TextFileReader;
import com.application.tree.interfaces.LaTeXTranslator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A class representing an Element's Summary.
 * Elements hold exact one or none Summary, the summaryText Represent the Text Line by Line
 * <p>
 * This class holds a List of Strings which represent the Summary of a Structure Element
 */
public class Summary implements LaTeXTranslator {
    private static final String START_SUMMARY = "%\\start{summary}";
    private static final String END_SUMMARY = "%\\finish{summary}";
    private final List<String> summaryText;

    /**
     * while active all lines in the document will be added to this summary, and therefor not to the TextBlock
     */
    private boolean listeningOnDocument;

    /**
     * Creates a new Summary instance.
     * <p>
     * This constructor initializes the summary list as an empty ArrayList and sets the
     * 'listeningOnDocument' status to false.
     */
    public Summary() {
        summaryText = new ArrayList<>();
        listeningOnDocument = false;
    }

    /**
     * extract the Summary from the Text
     * if the line starts/Ends set the Listener active
     * when Listener is active, then add the line to the Summary
     *
     * @param currentLine line to check for start/ end hints
     * @return true if line was added to summary, else false
     */
    public boolean extractSummary(String currentLine) {
        if (listeningOnDocument && currentLine.contains(END_SUMMARY)) {
            listeningOnDocument = false;
            return true;

        } else if (!listeningOnDocument && currentLine.contains(START_SUMMARY)) {
            listeningOnDocument = true;
            return true;

        } else if (listeningOnDocument) {
            String summaryLine = currentLine;
            summaryLine = TextFileReader.removeSpacesFromStart(summaryLine);
            summaryLine = removeFirstPercent(summaryLine);
            summaryText.add(summaryLine);
            return true;
        }
        return false;
    }

    /**
     *
     * @param inputString
     * @return
     */
    public String removeFirstPercent(String inputString) {
        if (inputString.contains("%")) {
            return inputString.replaceFirst("%", "");
        } else {
            return inputString;
        }
    }

    /**
     * returns the summary as a string with \n as delimiter, if there is no summary it returns null
     * @return summary as string
     */
    @Override
    public String toString() {
        if(summaryText.isEmpty()){
            return null;
        }
        return String.join("\n", summaryText);
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) {
        if(!exportSummary){
            return;
        }
        level++;

        String indentationHead = getIndentation(level);
        String indentationBody = getIndentation(level+1);
        StringBuilder text = map.get(key);

        text.append(indentationHead).append(START_SUMMARY).append("\n");
        for(String line : summaryText){
            text.append(indentationBody).append("% ").append(line).append("\n");
        }
        text.append(indentationHead).append(END_SUMMARY).append("\n").append("\n");
    }

    /**
     * never to be executed because the summary is the end
     */
    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) {
        assert false;
    }

    /**
     * never to be executed because the summary is the end
     */
    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) {
        assert false;
    }

    public List<String> getSummary() {
        return summaryText;
    }

    public void setSummary(String text) {
        this.summaryText.clear();
        this.summaryText.addAll(Arrays.asList(text.split("\n")));
    }

    public boolean isListeningOnDocument() {
        return this.listeningOnDocument;
    }
}