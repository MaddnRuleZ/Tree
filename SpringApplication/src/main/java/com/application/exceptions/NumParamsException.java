package com.application.exceptions;

/**
 * thrown, if the received JsonFile contains more than one command
 */
public class NumParamsException extends ProcessingException {

    /**
     * thrown if the received JsonFile misses a parameter
     * @param commandName name of the command that caused the error
     */
    public NumParamsException(String commandName) {
        super("Missing Parameter in JsonFile: " + commandName);
    }
}
