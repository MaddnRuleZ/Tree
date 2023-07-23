package com.Application.Exceptions;

/**
 * thrown, if the received JsonFile contains more than one command
 */
public class NumParamsException extends ProcessingException {

    /**
     * constructor
     * @param commandName name of the command that caused the error
     */
    public NumParamsException(String commandName) {
        super("Fehlender Parameter in JsonFile: " + commandName + "Dieser Fehler sollte nicht auftreten. Bitte überprüfen Sie die Erzeugung der Json-Dateien.");
    }
}
