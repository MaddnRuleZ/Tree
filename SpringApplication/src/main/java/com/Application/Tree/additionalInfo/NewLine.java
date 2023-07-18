package com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NewLine extends AdditionalInformationContainer {
    private final static String NEW_LN_CHARACTER = "\\newline";
    private final static String NEW_LN_CHARACTER1 = "\\\\";
    private final static String NEW_LN_CHARACTER2 = "\\par";

    protected List<String> content;

    public NewLine() {
        content = new ArrayList<>();
    }

    public static boolean checkLineForNewLineCharacters(String currentLine) {
        return currentLine.contains(NEW_LN_CHARACTER) || currentLine.contains(NEW_LN_CHARACTER1) || currentLine.contains(NEW_LN_CHARACTER2);
    }

    /**
     *
     * @param remainText
     * @return
     */
    public List<String> extractInfo(List<String> remainText) {
        // todo, just only save the nl
        List<List<String>> parts = new ArrayList<>();
        List<String> currentPart = new ArrayList<>();

        for (String line: remainText) {
            if (line.contains(NEW_LN_CHARACTER) || line.contains(NEW_LN_CHARACTER1) || line.contains(NEW_LN_CHARACTER2)) {
                parts.add(currentPart);
                currentPart = new ArrayList<>();
            }
            currentPart.add(line);
        }
        parts.add(currentPart);
        //content = parts;
        setNullContent();
        return null;
    }




    @Override
    protected void setNullContent() {
        if (content != null && content.size() == 0) {
            content = null;
        }
    }
}