package com.application.tree.interfaces;

import com.application.exceptions.UnknownElementException;

import java.util.Map;

public interface LaTeXTranslator {
    int INIT_INDENTATION_LEVEL = 0;
    /**
     * get the recompiled source code of the Latex Document
     * @param map           map of the LaTeX-Code
     * @param key           key of the map
     * @param level         level of indentation
     * @param exportComment if comments should be exported
     * @param exportSummary if summary should be exported
     * @throws UnknownElementException if an unknown element is found
     */
    void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException;

    /**
     * add the LaTeX-Code of summary and comments
     * @param map           map of the LaTeX-Code
     * @param key           key of the map
     * @param level         level of indentation
     * @param exportComment if comments should be exported
     * @param exportSummary if summary should be exported
     * @throws UnknownElementException if an unknown element is found
     */
    void toLaTeXStart(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException;

    /**
     * add the LaTeX-Code of the newLine
     * @param map           map of the LaTeX-Code
     * @param key           key of the map
     * @param level         level of indentation
     * @param exportComment if comments should be exported
     * @param exportSummary if summary should be exported
     * @throws UnknownElementException if an unknown element is found
     */
    void toLaTeXEnd(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException;

    /**
     * get the indentation for the LaTeX-Code
     * @param level level of the indentation
     * @return indentation
     */
    default String getIndentation(int level){
        return "\t".repeat(level);
    }

}
