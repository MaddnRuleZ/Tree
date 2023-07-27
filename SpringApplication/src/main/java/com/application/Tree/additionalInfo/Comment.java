package com.application.Tree.additionalInfo;

import com.application.Exceptions.UnknownElementException;
import com.application.Tree.interfaces.LaTeXTranslator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class representing an Element's Comments.
 *
 * Store each Comment in the Comment List
 */
public class Comment implements LaTeXTranslator {
    private final static String START_CHARACTER = "%";
    private final List<String> comments;

    /**
     * Constructs a new Comment object. Initializes the comment list as an empty ArrayList.
     */
    public Comment() {
        comments = new ArrayList<>();
    }

    /**
     * Extracts content from the provided currentLine and stores it in the comment list.
     *
     * @param currentLine The current line of code to be checked for comments.
     * @return true if a comment is found and added to the list, false otherwise.
     */
    public boolean extractContent(String currentLine) {
        if (currentLine.contains(START_CHARACTER)) {
            comments.add(currentLine);
            return true;
        }
        return false;
    }


    /**
     * returns the comment as a string with % as delimiter, if there is no comment it returns null
     * @return comment as string
     */
    public String toString() {
        if (comments.isEmpty()) {
            return null;
        }
        return String.join("%", comments);
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level) throws UnknownElementException {
        StringBuilder text = map.get(key);
        String indentation = getIndentation(level);

        for(String line : comments){
            text.append(indentation).append("%").append(line).append("\n");
        }
    }

    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
    }

    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
    }
}

