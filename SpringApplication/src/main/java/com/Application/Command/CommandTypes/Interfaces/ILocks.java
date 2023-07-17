package com.Application.Command.CommandTypes.Interfaces;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Interface that holds the lock for the treeStructure
 */
public interface ILocks {
    /**
     * ReadWriteLock on the treeStructure, always locks whole tree
     */
    ReentrantReadWriteLock structureLock = new ReentrantReadWriteLock();

    /**
     * trys to acquire the write structureLock, guarantees FIFO order
     */
    default void acquireStructureWriteLock() {
        while(!structureLock.writeLock().tryLock());
    }

    /**
     * releases the write structureLock
     */
    default void releaseStructureWriteLock() {
        structureLock.writeLock().unlock();
    }

    /**
     * trys to acquire the read structureLock, guarantees FIFO order
     */
    default void acquireStructureReadLock() {
        while(!structureLock.readLock().tryLock());
    }

    /**
     * releases the read structureLock
     */
    default void releaseStructureReadLock() {
        structureLock.readLock().unlock();
    }
}
