package com.application.command;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Manager that holds the lock for the treeStructure
 */
@SuppressWarnings("CanBeFinal")
public class LockManager {
    /**
     * ReadWriteLock on the treeStructure, always locks whole tree
     */
    ReentrantReadWriteLock structureLock;

    private static final LockManager instance = new LockManager();

    private LockManager() {
        structureLock = new ReentrantReadWriteLock();
    }

    /**
     * get the instance of the LockManager
     * @return the instance of the LockManager
     */
    public static LockManager getInstance() {
        return instance;
    }

    /**
     * try to acquire the write-structureLock
     */
    public void acquireStructureWriteLock() {
        structureLock.writeLock().lock();
    }

    /**
     * release the write-structureLock
     */
    public void releaseStructureWriteLock() {
        structureLock.writeLock().unlock();
    }

    /**
     * try to acquire the read-structureLock
     */
    public void acquireStructureReadLock() {
        structureLock.readLock().lock();
    }

    /**
     * release the read-structureLock
     */
    public void releaseStructureReadLock() {
        structureLock.readLock().unlock();
    }
}
