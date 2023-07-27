package com.application.exceptions;

@SuppressWarnings("SpellCheckingInspection")
public class FileInvalidException extends ProcessingException {
    public FileInvalidException(String message) {
        super("Fehler Bei der Dateibehandlung:" + message);
    }
}
