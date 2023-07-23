package com.Application.Exceptions;

public class ParseException extends ProcessingException {

    /**
     * constructor
     * @param message place where error occured
     */
    public ParseException(String message) {
        super("Beim Parsen ist ein Fehler aufgetreten: " + message + " \n Bitte überprüfen Sie die Syntax der Eingabe.");
    }
}
