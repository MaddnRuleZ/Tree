package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.ILocks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface Command {
    /**
     * executes the command
     */
    boolean execute();


    /**
     * generates the Json response String, if execution failed
     * @return failureResponse
     */
    default String generateFailureResponse() {
        //TODO
        return null;
    }

    String generateResponse();
}
