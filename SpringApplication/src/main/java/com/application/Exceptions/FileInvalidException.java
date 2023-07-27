package com.application.Exceptions;

public class FileInvalidException extends ProcessingException {
    public FileInvalidException(String message) {
        super("Fehler Bei der Dateibehandlung:" + message);
    }
}
