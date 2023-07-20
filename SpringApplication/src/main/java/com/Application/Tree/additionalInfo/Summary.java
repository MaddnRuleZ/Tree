package com.Application.Tree.additionalInfo;

import com.Application.Exceptions.UnknownElementException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public class Summary extends AdditionalInformationContainer {

    private static final String START_SUMMARY = "%\\start{summary}";
    private static final String END_SUMMARY = "%\\finish{summary}";
    private boolean active;
    private final List<String> summary;

    /**
     *
     */
    public Summary() {
        summary = new ArrayList<>();
        active = false;
    }

    /**
     *
     *
     * @param currentLine
     * @return
     */
    @Override
    public boolean extractContent(String currentLine) {
        if (active && currentLine.contains(END_SUMMARY)) {
            summary.add(currentLine);
            active = false;
            return true;

        } else if (!active && currentLine.contains(START_SUMMARY)) {
            active = true;
            summary.add(currentLine);
            return true;

        } else if (active) {
            summary.add(currentLine);
            return true;
        }

        return false;
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n");
        for(String line : summary){
            text.append(START_SUMMARY).append("\n");
            text.append(line).append("\n");
 text.append(END_SUMMARY).append("\n");
        }
    }
}