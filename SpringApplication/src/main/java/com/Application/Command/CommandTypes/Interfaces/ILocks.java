package com.Application.Command.CommandTypes.Interfaces;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface ILocks {
    /**
     * ReadWriteLock on the treeStructure, always locks whole tree
     */
    ReentrantReadWriteLock structureLock = new ReentrantReadWriteLock();


}
