package com.application.Exceptions;

/**
 * thrown if an element is not found
 */
public class ElementNotFoundException extends ProcessingException{

    /**
     * constructor
     */
    public ElementNotFoundException() {
        super("Ein Element zum Ausführen der Aktion konnte nicht gefunden werden.");
    }
}
