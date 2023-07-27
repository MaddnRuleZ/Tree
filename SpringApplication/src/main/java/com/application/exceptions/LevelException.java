package com.application.exceptions;

import com.application.tree.elements.ElementConfig;

@SuppressWarnings("SpellCheckingInspection")
public class LevelException extends ProcessingException{

    /**
     * constructor
     */
    public LevelException() {
        super("Sectioning Elemente dürfen nur bis zu einem Level von " + ElementConfig.SUBPARAGRAPH.getLevel() + " verschachtelt werden.");
    }
}
