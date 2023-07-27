package com.application.Exceptions;

/**
 * Exception that is thrown if an error occurs during printing
 */
public class PrintException extends ProcessingException {
    /**
     * creates a new PrintException
     */
    public PrintException() {
        super("Etwaige Änderungen konnten nicht gespeichert werden. \n " +
                "Sollten Sie das LaTeX-Projekt aus einer Datei geladen haben, kann es sein dass der LaTeX-Code dennoch in einer temporären Datei gespeichert wurde. \n");
    }
}
