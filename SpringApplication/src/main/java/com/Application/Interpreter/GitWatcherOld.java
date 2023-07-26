package com.Application.Interpreter;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Exceptions.ParseException;
import com.Application.Tree.elements.roots.Root;
import com.Application.Tree.elements.roots.Roots;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;


/**
 *
 * Don't use this class ---> newClass: GitWatcher
 *
 *
 *
 *
 *
 *
 *
 */
public class GitWatcherOld implements Runnable, ILocks {
    /**
     * Thread that watches for changes in the git repository
     */
    private Thread watcherThread;
    /**
     * time to sleep between checks
     */
    private final int sleepTime = 1000;
    /**
     * true if changes were found
     */
    private boolean changes = false;
    /**
     * path to the git repository
     */
    private String path;
    /**
     * tree structure
     */
    private Root root;
    /**
     * message to display if an error occurs
     */
    String failureMessage = "Error while searching for remote changes";

    /**
     * true if an error occurs
     */
    boolean failure = false;

    /**
     * creates a new watcher
     * @param path to the git repository
     * @param root tree structure
     */
    public GitWatcherOld(String path, Root root) {
        this.path = path;
        this.root = root;
    }

    /**
     * starts the watcher
     * checks for changes in the git repository all time seconds
     * if changes were found, the tree structure is updated
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
                boolean hasChanges = checkForRemoteChanges(path);
                if (hasChanges) {
                    acquireStructureWriteLock();
                    Parser parser = new Parser(path);
                    Roots parsed = parser.startParsing();
                    if (parsed instanceof Root) {
                        root = (Root) parsed;
                    } else {
                        throw new ParseException("return value of parsing" + root.getClass().getName());
                    }
                }
            } catch (GitAPIException | IOException | InterruptedException | ParseException e) {
                failure = true;
                break;
            } finally {
                releaseStructureWriteLock();
            }
        }
    }

    /**
     * checks for changes in the git repository
     * @param path to the git repository
     * @return true if changes were found
     * @throws GitAPIException
     * @throws IOException
     */
    private boolean checkForRemoteChanges(String path) throws GitAPIException, IOException {
        File localRepoDir = new File(path);
        Repository repository = new FileRepositoryBuilder().setGitDir(localRepoDir).build();
        Git git = new Git(repository);

        PullCommand pullCommand = git.pull();
        pullCommand.call();

        //TODO check for changes

        return false;
    }

    public void setWatcherThread(Thread watcherThread) {
        this.watcherThread = watcherThread;
    }

    public Thread getWatcherThread() {
        return watcherThread;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public boolean hasChanges() {
        return changes;
    }

    public void setChanges(boolean hasChanges) {
        this.changes = hasChanges;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public boolean isFailure() {
        return failure;
    }
}
