package com.Application.Exceptions;

/**
 * thrown, if the received JsonFile contains more than one command
 */
public class NumParamsException extends Exception {

    public NumParamsException(String errorMessage) {
        super(errorMessage);
    }
}
