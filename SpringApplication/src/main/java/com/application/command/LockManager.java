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
    private ReentrantReadWriteLock structureLock;

    private ReentrantReadWriteLock hasUpdatesGitWatcherLock;

    private ReentrantReadWriteLock hasRecentRequestsInterceptorLock;


    private static final LockManager instance = new LockManager();

    private LockManager() {
        this.hasRecentRequestsInterceptorLock = new ReentrantReadWriteLock();
        structureLock = new ReentrantReadWriteLock();
        hasUpdatesGitWatcherLock =  new ReentrantReadWriteLock();
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


    public void acquireGitWatcherReadLock() {
        hasUpdatesGitWatcherLock.readLock().lock();
    }

    public void releaseGitWatcherReadLock() {
        hasUpdatesGitWatcherLock.readLock().unlock();
    }

    public void acquireGitWatcherWriteLock() {
        hasUpdatesGitWatcherLock.writeLock().lock();
    }

    public void releaseGitWatcherWriteLock() {
        hasUpdatesGitWatcherLock.writeLock().unlock();
    }

    public void acquireInterceptorReadLock() {
        hasRecentRequestsInterceptorLock.readLock().lock();
    }

    public void releaseInterceptorReadLock() {
        hasRecentRequestsInterceptorLock.readLock().unlock();
    }

    public void acquireInterceptorWriteLock() {
        hasRecentRequestsInterceptorLock.writeLock().lock();
    }

    public void releaseInterceptorWriteLock() {
        hasRecentRequestsInterceptorLock.writeLock().unlock();
    }

}
