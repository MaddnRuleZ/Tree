package com.application.exceptions;


/**
 * always thrown if an error occurs during the execution of a command
 */
public class ProcessingException extends Exception{
    public ProcessingException(String message) {
        super(message);
    }
}
