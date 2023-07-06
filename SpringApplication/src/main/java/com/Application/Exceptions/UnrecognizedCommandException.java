package com.Application.Exceptions;

/**
 * thrown if the command type of the json file is not recognized
 */
public class UnrecognizedCommandException extends Exception {

    /**
     * thrown if the command type of the json file is not recognized
     * @param commandString unrecognized command
     */
    public UnrecognizedCommandException(String commandString) {
        super(commandString);
    }
}
