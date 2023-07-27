package com.application.exceptions;

/**
 * thrown if the command type of the json file is not recognized
 */
public class UnrecognizedCommandException extends ProcessingException {

    /**
     * thrown if the command type of the json file is not recognized
     * @param commandString unrecognized command
     */
    public UnrecognizedCommandException(String commandString) {
        super(commandString);
    }
}
