package com.Application.Exceptions;

public class TypeException extends ProcessingException {

    /**
     * constructor
     * @param required type required
     * @param found type found
     */
    public TypeException(String required, String found) {
        super("Das gesuchte Element hat den falschen Typ. Gesucht: " + required + " Gefunden: " + found);
    }
}
