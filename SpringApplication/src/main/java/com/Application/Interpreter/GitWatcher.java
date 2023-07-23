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

public class GitWatcher implements Runnable, ILocks {
    private Thread watcherThread;
    private final int sleepTime = 1000;
    private boolean changes = false;
    private String path;
    private Root root;

    String failureMessage = "Error while searching for remote changes";

    boolean failure = false;

    public GitWatcher(String path, Root root) {
        this.path = path;
        this.root = root;
    }

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
