package com.application.interpreter;

import com.application.RequestInterceptor;
import com.application.User;
import com.application.command.LockManager;
import com.application.exceptions.ProcessingException;
import com.application.printer.GitPrinter;
import com.application.tree.elements.roots.Root;
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

    /**
     * time threshold in milliseconds after which the structure is exported
     * At the moment, the threshold is set to 1 minutes
     */
    private final long timeThresholdInMilliseconds = 10000;

    private final RequestInterceptor requestInterceptor;


    @Autowired
    public GitWatcher(User user, RequestInterceptor requestInterceptor) {
        this.user = user;
        this.requestInterceptor = requestInterceptor;
        this.lockManager = LockManager.getInstance();
    }

    /**
     * checks for changes in the git repository
     * if changes were found, the structure will be updated
     */
    @Scheduled(fixedRate = timeThresholdInMilliseconds)
    public void check() {
        if(user.getPrinter() != null && user.getPrinter() instanceof GitPrinter) {
            GitPrinter printer = (GitPrinter) user.getPrinter();
            System.out.println("Checking for changes in git repository");

                try {
                    this.lockManager.acquireStructureWriteLock();
                    printer.print();
                    if (printer.checkForChanges()) {
                        System.out.println("Git has changes");
                        Root.resetInstance();
                        Parser parser = new Parser(printer.getPath());
                        user.setRoot((Root) parser.startParsingText());
                        System.out.println(user.getRoot().toJsonEditor());
                        changes = true;
                    }
                    boolean noRecentRequests = requestInterceptor.hasNoRecentRequests();
                    if (!noRecentRequests && requestInterceptor.hasChanges()) {
                        printer.commitAndPush();
                    }
                } catch (ProcessingException | IOException e) {
                    failureMessage = e.getMessage();
                    failure = true;
                } finally {
                    this.requestInterceptor.resetChanges();
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

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }
}
