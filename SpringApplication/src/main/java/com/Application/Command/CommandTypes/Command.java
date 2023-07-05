package main.java.com.Application.Command.CommandTypes;

public abstract class Command {
    /**
     * root of tree structure
     */
    //private Root root;

    /**
     * executes the command
     */
    public abstract void execute();

}
