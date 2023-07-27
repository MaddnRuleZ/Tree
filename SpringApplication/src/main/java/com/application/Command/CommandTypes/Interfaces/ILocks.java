package com.application.Command.CommandTypes.Interfaces;

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
     * try to acquire the write-structureLock
     */
    default void acquireStructureWriteLock() {
        while(!structureLock.writeLock().tryLock());
    }

    /**
     * release the write-structureLock
     */
    default void releaseStructureWriteLock() {
        structureLock.writeLock().unlock();
    }

    /**
     * try to acquire the read-structureLock
     */
    default void acquireStructureReadLock() {
        while(!structureLock.readLock().tryLock());
    }

    /**
     * release the read-structureLock
     */
    default void releaseStructureReadLock() {
        structureLock.readLock().unlock();
    }
}
