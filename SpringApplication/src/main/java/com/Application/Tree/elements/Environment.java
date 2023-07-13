package com.Application.Tree.elements;

import com.Application.Interpreter.TextFileReader;
import com.Application.Tree.Element;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Environment extends Parent {

    public Environment(String startPart, String endPart, int startIndex, int level) {
        super(startPart, endPart, startIndex, level);

    }

    /**
     * Read the contents of the structure Element
     * mini Parser For Comments, Summaries and NewLines, okay by now resorted to c,s,p. Max: nC, 1S nP
     *
     * text uselsess after this transformation, text is only in the newline characters
     *
     * @param text
     * @param endIndex
     */
    public void generateTextFromIndices(String[] text, int endIndex) {
        if (endIndex != 0 && this.text == null) {
            String[] elementFullText = TextFileReader.extractStrings(text, this.startIndex +1, endIndex -1); // +1; -1 for removal of overlapping
            List<String> remainingText = Arrays.stream(elementFullText).toList();
            this.options = extractOptionsString(text[this.startIndex]);
            this.text = elementFullText;

            remainingText = summary.extractInfo(remainingText);
            remainingText = comment.extractInfo(remainingText);

            newLine.extractInfo(remainingText);
        }
    }

    @Override
    public Element searchForID(UUID id, int level) {
        if (this.getId().equals(id)) {
            return this;
        } else {
            for (Element child: this.getChildElements()) {
                Element foundElement = child.searchForID(id, level);
                if (foundElement != null) {
                    return foundElement;
                }
            }
        }
        return null;
    }
}
