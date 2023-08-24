package com.application.exceptions;

public class OverleafGitException extends ProcessingException{
    public OverleafGitException(String message) {
        super("Overleaf Fehler:" + message);
    }
}
