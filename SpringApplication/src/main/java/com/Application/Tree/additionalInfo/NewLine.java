package com.Application.Tree.additionalInfo;

import com.Application.Exceptions.UnknownElementException;

import java.util.Map;

/**
 *
 *
 */
public class NewLine extends AdditionalInformationContainer {
    private final static String[] NEW_LN_CHARACTERS = {
            "\\newline",
            "\\\\",
            "\\par"
    };
    private String newLine;

    @Override
    public boolean extractContent(String currentLine) {
        if (checkLineForNewLineCharacters(currentLine)) {
            this.newLine = currentLine;
            return true;
        }
        return false;
    }

    public static boolean checkLineForNewLineCharacters(String currentLine) {
        for (String nlChar: NEW_LN_CHARACTERS) {
            if (currentLine.contains(nlChar)) {
                return true;
            }
        }
        return false;
    }

    public String getNewLine() {
        return newLine;
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n").append(newLine).append("\n");
    }
}