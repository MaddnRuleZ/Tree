package com.Application.Exceptions;

/**
 * thrown, if the received JsonFile contains more than one command
 */
public class NumParamsException extends ProcessingException {

    public NumParamsException(String commandName) {
        super("Missing Parameter in " + commandName);
    }
}
