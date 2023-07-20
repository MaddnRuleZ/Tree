package com.Application.Tree.interfaces;

import com.Application.Exceptions.UnknownElementException;

import java.util.Map;

public interface LaTeXTranslator {
    /**
     * get the recompiled source code of the Latex Doc
     * @return
     */
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException;

}
