package com.application.interpreter;

import com.application.command.types.interfaces.ILocks;
import com.application.printer.GitPrinter;
import com.application.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * watches the git repository for changes
 * if changes were found, the structure will be updated
 */
@Component
public class GitWatcher implements ILocks {

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
    }

    /**
     * checks for changes in the git repository
     * if changes were found, the structure will be updated
     */
    @Scheduled(fixedRate = 60000)
    public void check() {
        if(user.getPrinter() != null && user.getPrinter() instanceof GitPrinter) {
            GitPrinter printer = (GitPrinter) user.getPrinter();
            /* TODO
            if(printer.isRemoteRepositoryChanged()) {
                try {
                    acquireStructureReadLock();
                    printer.pullRepository();


                    changes = true;
                } catch (ProcessingException e) {
                    failureMessage = e.getMessage();
                    failure = true;
                } finally {
                    releaseStructureReadLock();
                }
            }*/
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
