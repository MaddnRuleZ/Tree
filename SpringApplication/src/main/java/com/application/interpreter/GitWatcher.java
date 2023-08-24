package com.application.interpreter;

import com.application.User;
import com.application.command.LockManager;
import com.application.exceptions.OverleafGitException;
import com.application.exceptions.ProcessingException;
import com.application.printer.GitPrinter;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * watches the git repository for changes
 * if changes were found, the structure will be updated
 */
@Component
public class GitWatcher {
    /**
     * lockManager for locking the structure Lock
     */
    private LockManager lockManager;

    /**
     * user that holds information of LaTeX-Project
     */
    private final User user;

    /**
     * message to display if an error occurs
     */
    String failureMessage = null;

    /**
     * true if an error occurs
     */
    boolean failure = false;
    /**
     * true if changes were found
     */
    private boolean changes = false;


    @Autowired
    public GitWatcher(User user) {
        this.user = user;
        this.lockManager = LockManager.getInstance();
    }

    /**
     * checks for changes in the git repository
     * if changes were found, the structure will be updated
     */
    @Scheduled(fixedRate = 60000)
    public void check() {
        if(user.getPrinter() != null && user.getPrinter() instanceof GitPrinter) {
            GitPrinter printer = (GitPrinter) user.getPrinter();
            System.out.println("Checking for changes in git repository");

                try {
                    if (printer.checkForChanges()) {
                        this.lockManager.acquireStructureWriteLock();
                        printer.export();
                        changes = true;
                    }
                } catch (ProcessingException | IOException e) {
                    failureMessage = e.getMessage();
                    failure = true;
                } finally {
                    this.lockManager.releaseStructureWriteLock();
                }

        }
    }

    public boolean hasChanges() {
        return changes;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public boolean isFailure() {
        return failure;
    }
}
