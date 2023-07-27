package com.application.exceptions;

/**
 * thrown if an element is not found
 */
@SuppressWarnings("SpellCheckingInspection")
public class ElementNotFoundException extends ProcessingException{

    /**
     * thrown if an element is not found
     */
    public ElementNotFoundException() {
        super("Ein Element zum Ausführen der Aktion konnte nicht gefunden werden.");
    }
}
