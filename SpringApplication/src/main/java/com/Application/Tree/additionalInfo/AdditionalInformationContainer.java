package com.Application.Tree.additionalInfo;
import com.Application.Tree.interfaces.LaTeXTranslator;

/**
 * for extracting Text stuff -> interface?
 *
 */
public abstract class AdditionalInformationContainer implements LaTeXTranslator {
    public abstract boolean extractContent(String currentLine);
}
