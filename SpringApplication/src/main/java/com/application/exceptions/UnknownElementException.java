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
     * thrown if input url is null
     * @param name
     * @param url
     */
    public UnknownElementException(String name, String url) {
        super("Beim Ausführen des Befehls ist ein unbekanntes Element aufgetreten: " + name + " Url: " + url);
    }
}
