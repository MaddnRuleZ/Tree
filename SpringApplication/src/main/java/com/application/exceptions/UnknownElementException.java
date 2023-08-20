package com.application.exceptions;

/**
 * thrown if an unknown element is found
 */
@SuppressWarnings("SpellCheckingInspection")
public class UnknownElementException extends ProcessingException{

    /**
     * constructor
     * @param name type of the element that caused the error
     */
    public UnknownElementException(String name) {
        super("Beim Ausführen des Befehls ist ein unbekanntes Element aufgetreten: " + name);
    }

    /**
     * thrown if an unknown element is found
     * @param found type of the element that caused the error
     * @param requested type of the element that was requested
     */
    public UnknownElementException(String found, String requested) {
        super("Beim Ausführen des Befehls ist ein unbekanntes Element aufgetreten: " + found + " Gesucht: " + requested);
    }
}
