package com.Application.Command.CommandTypes;

public abstract class Command {
    /**
     * root of tree structure
     */
    //private final Root root;

    /**
     * executes the command
     */
    public abstract void execute();

}
