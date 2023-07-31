package com.application.tree.additionalInfo;

import com.application.exceptions.UnknownElementException;
import com.application.tree.interfaces.LaTeXTranslator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class representing an Element's Summary.
 *
 * This class holds a List of Strings which represent the Summary of a Structure Element
 */
public class Summary implements LaTeXTranslator {
    private static final String START_SUMMARY = "%\\start{summary}";
    private static final String END_SUMMARY = "%\\finish{summary}";
    /**
     * Line by Line the Text of the Summary
     */
    private final List<String> summaryText;

    /**
     * while active all lines in the document will be added to this summary, and therefor not to the TextBlock
     */
    private boolean listeningOnDocument;

    /**
     * Creates a new Summary instance.
     *
     * This constructor initializes the summary list as an empty ArrayList and sets the
     * 'listeningOnDocument' status to false.
     */
    public Summary() {
        summaryText = new ArrayList<>();
        listeningOnDocument = false;
    }

    /**
     * if the line starts/Ends the Summary or the Listener is active, then add the line to the Summary
     *
     * @param currentLine line to check for start/ end hints
     * @return true if line was added to summary, else false
     */
    public boolean extractContent(String currentLine) {
        if (listeningOnDocument && currentLine.contains(END_SUMMARY)) {
            summaryText.add(currentLine);
            listeningOnDocument = false;
            return true;

        } else if (!listeningOnDocument && currentLine.contains(START_SUMMARY)) {
            listeningOnDocument = true;
            summaryText.add(currentLine);
            return true;

        } else if (listeningOnDocument) {
            summaryText.add(currentLine);
            return true;
        }
        return false;
    }

    /**
     * returns the summary as a string with . as delimiter, if there is no summary it returns null
     * @return summary as string
     */
    @Override
    public String toString() {
        if(summaryText.isEmpty()){
            return null;
        }
        return String.join(".", summaryText);
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level) throws UnknownElementException {
        String indentationHead = getIndentation(level);
        String indentationBody = getIndentation(level+1);
        StringBuilder text = map.get(key);

        text.append(indentationHead).append("\n").append("\n");
        text.append(indentationHead).append(START_SUMMARY).append("\n");
        for(String line : summaryText){
            text.append(indentationBody).append("\t".repeat(level)).append("%").append(line).append("\n");
        }
        text.append(indentationHead).append(END_SUMMARY).append("\n");
    }

    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
    }

    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
    }

    public List<String> getSummary() {
        return summaryText;
    }

    public void setSummary(String text) {
        this.summaryText.clear();
        this.summaryText.add(text);
    }
}