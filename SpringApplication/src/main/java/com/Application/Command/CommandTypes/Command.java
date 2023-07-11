package com.Application.Command.CommandTypes;

public interface Command {
    /**
     * executes the command
     */
    String execute();


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
