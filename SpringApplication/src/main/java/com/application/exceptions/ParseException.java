package com.application.exceptions;

@SuppressWarnings("SpellCheckingInspection")
public class ParseException extends ProcessingException {

    public ParseException() {
        super("Die Eingabe hat beim Parsen einen Baum erzeugt. \n LaTeX-Code ist an dieser Stelle jedoch nicht erlaubt." +
                " Bitte vermeiden Sie die Nutzung von Syntax wie bspw. \\section{} oder \\begin{}, \\end{}");
    }

    /**
     * thrown if no element could be parsed
     * received by user
     * @param input input that could not be parsed
     */
    public ParseException(String input) {
        super("Beim Parsen ist ein Fehler aufgetreten. \n Bitte überprüfen Sie die Syntax der Eingabe. Ihre Eingabe: " + input);
    }

     /**
     * thrown if parser returns wrong type
     * should not be received by user
     * @param commandType place where it happened
     * @param found type that was found
     * @param requested type that was requested
     */
    public ParseException(String commandType, String found, String requested) {
        super("Parsing Error in " + commandType + ": " + "Found " + found + " but expected " + requested + " \n Something is wrong with Parser");
    }
}
