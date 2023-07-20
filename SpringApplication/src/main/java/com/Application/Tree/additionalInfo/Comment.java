package com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class Comment extends AdditionalInformationContainer {
    private final static String START_CHARACTER = "%";
    private final List<String> comment;



    public Comment() {
        comment = new ArrayList<>();
    }

    @Override
    public boolean extractContent(String currentLine) {
        if (currentLine.contains(START_CHARACTER)) {
            comment.add(currentLine);
            return true;
        }
        return false;
    }


    /**
     * returns the comment as a string with % as delimiter
     * @return comment as string
     */
    public String toString() {
        return String.join("%", comment);
    }
}

