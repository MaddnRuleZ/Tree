package com.application.printer;

import com.application.command.LockManager;
import com.application.RequestInterceptor;
import com.application.exceptions.PrintException;
import com.application.exceptions.ProcessingException;
import com.application.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * watches the HTTP requests and detects if there are recent requests
 * if there are no recent requests, the structure will be exported
 * {@link RequestInterceptor}
 */
@Component
public class AutoExport {

    private final LockManager lockManager;
    /**
     * Interceptor to check for recent requests
     */
    private final RequestInterceptor requestInterceptor;
    /**
     * user containing printer and root
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
     * time threshold in milliseconds after which the structure is exported
     * At the moment, the threshold is set to 1 minutes
     */
    private final long timeThresholdInMilliseconds = 1 * 60 * 1000;

    @Autowired
    public AutoExport(RequestInterceptor requestInterceptor, User user) {
        this.requestInterceptor = requestInterceptor;
        this.user = user;
        this.lockManager = LockManager.getInstance();
    }


    //TODO currently not executed --> remove false statement
    @Scheduled(fixedRate = timeThresholdInMilliseconds)
    public void check() {
        if(user.getPrinter() != null) { //<---remove false statement
            System.out.println("checking");
            boolean noRecentRequests = requestInterceptor.hasNoRecentRequests();

            if (!noRecentRequests && requestInterceptor.hasChanges()) {
                try {
                    this.lockManager.acquireStructureReadLock();
                    user.getPrinter().export();

                } catch (ProcessingException e) {
                    failureMessage = e.getMessage();
                    failure = true;
                } catch (IOException e) {
                    failureMessage = new PrintException().getMessage();
                    failure = true;
                } finally {
                    requestInterceptor.resetChanges();
                    this.lockManager.releaseStructureReadLock();
                }
            }
        }

    }
    public String getFailureMessage() {
        return failureMessage;
    }

    public boolean isFailure() {
        return failure;
    }

}
