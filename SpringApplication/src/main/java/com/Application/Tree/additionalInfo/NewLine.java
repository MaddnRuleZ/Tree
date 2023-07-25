package com.Application.Tree.additionalInfo;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.interfaces.LaTeXTranslator;

import java.util.Map;

/**
 * A class representing an Element's NewLine Parts.
 *
 * NewLines Seper two TextBLockElements from each other. And will be stored in this Additional Container
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
     * Extracts content from the provided currentLine and stores it as the new line content if any
     * new line character is found.
     *
     * @param currentLine The current line of code to be checked for new line characters.
     * @return true if a new line character is found and set as the new line content, false otherwise.
     */
    public boolean extractContent(String currentLine) {
        if (checkLineForNewLineCharacters(currentLine)) {
            this.newLine = currentLine;
            return true;
        }
        return false;
    }

    /**
     * Checks the provided currentLine for the presence of any new line characters.
     *
     * @param currentLine The current line of code to be checked for new line characters.
     * @return true if any new line character is found in the current line, false otherwise.
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
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n").append(newLine).append("\n");
    }

    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key) throws UnknownElementException {
    }

    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key) throws UnknownElementException {
    }
}