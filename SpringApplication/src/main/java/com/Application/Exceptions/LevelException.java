package com.Application.Exceptions;

import com.Application.Tree.elements.ElementConfig;

public class LevelException extends ProcessingException{

    /**
     * constructor
     */
    public LevelException() {
        super("Sectioning Elemente dürfen nur bis zu einem Level von " + ElementConfig.SUBPARAGRAPH.getLevel() + " verschachtelt werden.");
    }
}
