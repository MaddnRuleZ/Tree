package com.application.exceptions;

/**
 * thrown if an error occurs while generating the response
 */
@SuppressWarnings("SpellCheckingInspection")
public class GeneratingResponseException extends ProcessingException{

    /**
     * thrown if an error occurs while generating the response
     */
    public GeneratingResponseException() {
        super("Fehler beim Generieren der Antwort. Es wird empfohlen das Programm zu beenden und das LaTeX-Dokument manuell zu überprüfen.");
    }
}
