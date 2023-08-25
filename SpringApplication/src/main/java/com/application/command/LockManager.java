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
    private final ReentrantReadWriteLock structureLock;

    /**
     * ReadWriteLock on the gitWatcher hasChanges
     */
    private final ReentrantReadWriteLock hasUpdatesGitWatcherLock;


    /**
     * ReadWriteLock on the interceptor hasChanges
     */
    private final ReentrantReadWriteLock hasRecentRequestsInterceptorLock;

    /**
     * instance of the LockManager
     */
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

    /**
     * try to acquire the write-hasUpdatesGitWatcherLock
     */
    public void acquireGitWatcherReadLock() {
        hasUpdatesGitWatcherLock.readLock().lock();
    }

    /**
     * release the write-hasUpdatesGitWatcherLock
     */
    public void releaseGitWatcherReadLock() {
        hasUpdatesGitWatcherLock.readLock().unlock();
    }
    /**
     * try to acquire the write-hasUpdatesGitWatcherLock
     */
    public void acquireGitWatcherWriteLock() {
        hasUpdatesGitWatcherLock.writeLock().lock();
    }
    /**
     * release the write-hasUpdatesGitWatcherLock
     */
    public void releaseGitWatcherWriteLock() {
        hasUpdatesGitWatcherLock.writeLock().unlock();
    }

    /**
     * try to acquire the write-hasRecentRequestsInterceptorLock
     */
    public void acquireInterceptorReadLock() {
        hasRecentRequestsInterceptorLock.readLock().lock();
    }

    /**
     *  release the write-hasRecentRequestsInterceptorLock
     */
    public void releaseInterceptorReadLock() {
        hasRecentRequestsInterceptorLock.readLock().unlock();
    }

    /**
     * try to acquire the write-hasRecentRequestsInterceptorLock
     */
    public void acquireInterceptorWriteLock() {
        hasRecentRequestsInterceptorLock.writeLock().lock();
    }

    /**
     * release the write-hasRecentRequestsInterceptorLock
     */
    public void releaseInterceptorWriteLock() {
        hasRecentRequestsInterceptorLock.writeLock().unlock();
    }

}
