package com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Comment extends AdditionalInformationContainer {
    private final static String START_CHARACTER = "%";
    public Comment() {
        super();
    }

    /**
     * returns the remaining Text
     *
     * @param remainText
     * @return
     */
    public List<String> extractInfo(List<String> remainText) {
        ArrayList<String> remainingText = new ArrayList<>();
        this.content = new ArrayList<>();

        for (String line: remainText) {
            if (line.contains(START_CHARACTER)) {
                this.content.add(line);
            } else {
                remainingText.add(line);
            }
        }
        setNullContent();
        return remainingText;
    }
}

