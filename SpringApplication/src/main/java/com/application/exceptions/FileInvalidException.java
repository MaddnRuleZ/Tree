package com.application.exceptions;

public class FileInvalidException extends ProcessingException {
    public FileInvalidException(String message) {
        super("Fehler Bei der Dateibehandlung:" + message);
    }
}
