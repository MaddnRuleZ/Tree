package com.Application.Interpreter.additionalInfo;


import java.util.ArrayList;
import java.util.List;

public class TextBlock extends AdditionalInformationContainer {
    private final static String NEW_LN_CHARACTER = "\\newline";
    private final static String NEW_LN_CHARACTER1 = "\\\\";

    protected List<List<String>> content;

    public TextBlock() {
        content = new ArrayList<>();
    }

    /**
     *
     * @param remainText
     * @return
     */
    public List<String> extractInfo(List<String> remainText) {
        List<List<String>> parts = new ArrayList<>();
        List<String> currentPart = new ArrayList<>();

        for (String line: remainText) {
            if (line.contains(NEW_LN_CHARACTER) || line.contains(NEW_LN_CHARACTER1)) {
                parts.add(currentPart);
                currentPart = new ArrayList<>();
            }
            currentPart.add(line);
        }
        parts.add(currentPart);
        content = parts;
        setNullContent();
        return null;
    }

    public List<List<String>> getTextParts() {
        return this.content;
    }

    @Override
    protected void setNullContent() {
        if (content != null && content.size() == 0) {
            content = null;
        }
    }
}