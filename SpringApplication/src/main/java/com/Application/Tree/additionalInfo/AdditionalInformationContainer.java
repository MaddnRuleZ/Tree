package com.Application.Tree.additionalInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * for extracting Text stuff -> interface?
 *
 */
public abstract class AdditionalInformationContainer {
    public AdditionalInformationContainer() {
    }
    public abstract boolean extractContent(String currentLine);
}
