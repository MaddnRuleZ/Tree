package com.Application.Command.CommandTypes;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface Command {
    /**
     * executes the command
     */
    String execute();

    /**
     * generates the Json response String
     * @return response
     */
    String generateResponse();

}
