package com.Application.Command.CommandTypes.Interfaces;

/**
 * Interface that moves an element in the treeStructure
 */
public interface IMoveElementCommand {
    /**
     * moves an element in the treeStructure
     * @return true if the element was moved successfully, false otherwise
     */

    default boolean moveElement() {
        //TODO
        return false;
    }

}
