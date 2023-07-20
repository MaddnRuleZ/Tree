package com.Application.Tree.additionalInfo;


import com.Application.Tree.interfaces.JsonParser;
import com.Application.Tree.interfaces.LaTeXTranslator;

import java.util.ArrayList;
import java.util.List;

/**
 * for extracting Text stuff -> interface?
 *
 */
public abstract class AdditionalInformationContainer implements LaTeXTranslator {
    public AdditionalInformationContainer() {
    }
    public abstract boolean extractContent(String currentLine);
}
