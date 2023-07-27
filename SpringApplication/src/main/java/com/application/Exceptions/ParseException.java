package com.application.Exceptions;

public class ParseException extends ProcessingException {

    /**
     * constructor
     * @param message place where error occurred
     */
    public ParseException(String message) {
        super("Beim Parsen ist ein Fehler aufgetreten: " + message + " \n Bitte überprüfen Sie die Syntax der Eingabe.");
    }
}
