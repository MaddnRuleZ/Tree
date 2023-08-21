package com.application.tree.additionalInfo;

import com.application.interpreter.TextFileReader;
import com.application.tree.interfaces.LaTeXTranslator;

import java.util.Map;

/**
 * A class representing an Element's NewLine Parts.
 *
 * NewLines Separate two TextBLockElements from each other. And will be stored in this Additional Container
 * Each TextBLockElement can only have one NewLineCharacter and multiple in one line will all be stored in one line too.
 */
public class NewLine implements LaTeXTranslator {

    /**
     * Global Collection of all Strings that will seper two TextBlockElements from each other
     */
    private final static String[] NEW_LN_CHARACTERS = {
            "\\newline",
            "\\\\",
            "\\par"
    };
    private String newLine;

    /**
     * if found, extracts the newline Character from the provided currentLine and stores it
     * as the new line content if any
     *
     * @param currentLine The current line of code, to be checked for new line characters.
     * @return true if newLine character found, false otherwise.
     */
    public boolean extractNlChar(String currentLine) {
        if (checkLineForNewLineCharacters(currentLine)) {
            this.newLine = TextFileReader.removeSpacesFromStart(currentLine);
            return true;
        }
        return false;
    }

    /**
     * Checks the provided currentLine for presence of any new line characters.
     *
     * @param currentLine The current line to be checked for new line characters.
     * @return true if  NL -Character is found, false otherwise
     */
    public boolean checkLineForNewLineCharacters(String currentLine) {
        for (String nlChar: NEW_LN_CHARACTERS) {
            if (currentLine.contains(nlChar)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) {
        String indentation = getIndentation(level);
        StringBuilder text = map.get(key);

        // e.g. \newline, \\, \par
        text.append(indentation).append(newLine).append("\n");
    }

    /**
     * never to be executed because NewLine is the End
     */
    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) {
        assert false;
    }

    /**
     * never to be executed because NewLine is the End
     */
    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) {
        assert false;
    }

    public String getNewLine() {
        return newLine;
    }
}