package com.Application.Interpreter.additionalInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * for extracting Text stuff -> interface?
 *
 */
public abstract class AdditionalInformationContainer {

    protected List<String> content;
    public AdditionalInformationContainer() {
        content = new ArrayList<>();
    }

    public abstract List<String> extractInfo(List<String> remainingText);

    /**
     * set content null on empty list
     */
    protected void setNullContent() {
        if (content != null && content.size() == 0) {
            content = null;
        }
    }

    public List<String> getContent() {
        return this.content;
    }

    public void setContent(String comment) {
        this.content = content;
    }
}
