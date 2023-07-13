package com.Application.Tree.elements.environments;

import com.Application.Interpreter.TextFileReader;
import com.Application.Tree.elements.Parent;
import com.Application.Tree.elements.EnvElements.Caption;
import com.Application.Tree.elements.EnvElements.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Environment extends Parent {
    protected List<Label> labelList;
    protected List<Caption> captionList;

    public Environment(String startPart, String endPart, int startIndex) {
        super(startPart, endPart, startIndex);

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
        if (endIndex != 0 && validateIndicTextGeneration()) {
            String[] elementFullText = TextFileReader.extractStrings(text, this.startIndex +1, endIndex -1); // +1; -1 for removal of overlapping
            List<String> remainingText = Arrays.stream(elementFullText).toList();
            this.options = extractOptionsString(text[this.startIndex]);
            this.text = elementFullText;

            remainingText = summary.extractInfo(remainingText);
            remainingText = comment.extractInfo(remainingText);

            // extract labels and captions
            newLine.extractInfo(remainingText);
        }
    }


    // exec, when finnishing an environment?
    private List<String> scanElementForLabel(List<String> remainingText) {
        List<String> restText = new ArrayList<>();
        labelList = new ArrayList<>();
        for (String line: remainingText) {
            if (line.contains(Label.startPart)) {
                //TODO ERROR
                //labelList.add(new Label(line));
            } else {
                restText.add(line);
            }
        }
        return restText;
    }

    // identical
    private List<String> scanElementForCaption(List<String> remainingText) {
        List<String> restText = new ArrayList<>();
        captionList = new ArrayList<>();
        for (String line: remainingText) {
            if (line.contains(Caption.startPart)) {
                //TODO ERROR
                //captionList.add(new Caption(line));
            } else {
                restText.add(line);
            }
        }
        return restText;
    }

}
