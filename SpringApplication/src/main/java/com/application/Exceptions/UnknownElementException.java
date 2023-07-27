package com.application.Exceptions;

/**
 * thrown if an unknown element is found
 */
public class UnknownElementException extends ProcessingException{

    /**
     * constructor
     * @param name type of the element that caused the error
     */
    public UnknownElementException(String name) {
        super("Beim Ausführen des Befehls ist ein unbekanntes Element aufgetreten: " + name);
    }
}
