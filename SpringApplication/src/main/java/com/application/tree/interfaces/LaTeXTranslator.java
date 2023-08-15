package com.application.tree.interfaces;

import com.application.exceptions.UnknownElementException;

import java.util.Map;

public interface LaTeXTranslator {
    int INIT_INDENTATION_LEVEL = 0;
    /**
     * get the recompiled source code of the Latex Doc
     * @return
     */
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException;

    /**
     * add the LaTeX-Code of summary and comments
     *
     * @param map           map of the LaTeX-Code
     * @param key           key of the map
     * @param level
     * @param exportComment
     * @param exportSummary
     * @throws UnknownElementException
     */
    public void toLaTeXStart(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException;

    /**
     * add the LaTeX-Code of the newLine
     *
     * @param map           map of the LaTeX-Code
     * @param key           key of the map
     * @param level
     * @param exportComment
     * @param exportSummary
     * @throws UnknownElementException
     */
    public void toLaTeXEnd(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException;

    /**
     * get the indentation for the LaTeX-Code
     * @param level level of the indentation
     * @return indentation
     */
    default String getIndentation(int level){
        return "\t".repeat(level);
    }

}
