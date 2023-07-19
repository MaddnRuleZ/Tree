package com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Comment extends AdditionalInformationContainer {
    private final static String START_CHARACTER = "%";
    private String comment;

    public Comment() {
        super();
    }

    /**
     * returns the remaining Text
     *
     * @param currentLine
     * @return
     */
    public boolean extractInfo(String currentLine) {
        if (currentLine.contains(START_CHARACTER)) {

        }



    }
}

