package com.application.exceptions;

@SuppressWarnings("SpellCheckingInspection")
public class TypeException extends ProcessingException {

    /**
     * thrown if the type of element is not the required one
     * @param required type required
     * @param found type found
     */
    public TypeException(String required, String found) {
        super("Das gesuchte Element hat den falschen Typ. Gesucht: " + required + " Gefunden: " + found);
    }
}
