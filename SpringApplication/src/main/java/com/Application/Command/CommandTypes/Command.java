package com.Application.Command.CommandTypes;

public interface Command {
    /**
     * executes the command
     */
    public abstract String execute();

    /**
     * generates the Json response String
     * @return response
     */
    public abstract String generateResponse();

}
