package com.Application.Tree.additionalInfo;
import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.interfaces.LaTeXTranslator;

import java.util.Map;

/**
 * for extracting Text stuff -> interface?
 *
 */
public abstract class AdditionalInformationContainer implements LaTeXTranslator {
    public abstract boolean extractContent(String currentLine);


    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        return;
    }

    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        return;
    }
}
