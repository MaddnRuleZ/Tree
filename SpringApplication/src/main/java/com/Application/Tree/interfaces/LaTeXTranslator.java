package com.Application.Tree.interfaces;

import com.Application.Exceptions.UnknownElementException;

import java.util.Map;

public interface LaTeXTranslator {
    /**
     * get the recompiled source code of the Latex Doc
     * @return
     */
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException;

    /**
     * add the LaTeX-Code of summary and comments
     * @param map map of the LaTeX-Code
     * @param key key of the map
     * @throws UnknownElementException
     */
    public void toLaTeXStart(Map<String,StringBuilder> map, String key) throws UnknownElementException;

    /**
     * add the LaTeX-Code of the newLine
     * @param map map of the LaTeX-Code
     * @param key key of the map
     * @throws UnknownElementException
     */
    public void toLaTeXEnd(Map<String,StringBuilder> map, String key) throws UnknownElementException;

}
