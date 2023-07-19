package com.Application.Tree.additionalInfo;


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
    public List<String> getContent() {
        return this.content;
    }
}
