package com.Application.Interpreter;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Command.RequestInterceptor;
import com.Application.Exceptions.ParseException;
import com.Application.Printer.GitPrinter;
import com.Application.Tree.elements.roots.Root;
import com.Application.Tree.elements.roots.Roots;
import com.Application.User;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GitWatcher implements ILocks {

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
        if(user.getPrinter() != null || user.getPrinter() instanceof GitPrinter) {
            /*
            try {
                //TODO checkForUpdates
                acquireStructureWriteLock();
                //TODO updateStructure
            } catch (GitAPIException | IOException | InterruptedException | ParseException e) {
                failure = true;
                failureMessage = e.getMessage();
            } finally {
                releaseStructureWriteLock();
            }
            */
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
