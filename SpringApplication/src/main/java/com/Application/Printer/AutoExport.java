package com.Application.Printer;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Command.RequestInterceptor;
import com.Application.Exceptions.PrintException;
import com.Application.Exceptions.ProcessingException;
import com.Application.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AutoExport implements ILocks {
    private final RequestInterceptor requestInterceptor;
    private final User user;
    /**
     * message to display if an error occurs
     */
    String failureMessage = null;

    /**
     * true if an error occurs
     */
    boolean failure = false;

    @Autowired
    public AutoExport(RequestInterceptor requestInterceptor, User user) {
        this.requestInterceptor = requestInterceptor;
        this.user = user;
    }


    //TODO currently not executed --> remove false statement
    @Scheduled(fixedRate = 60000)
    public void check() {
        if(user.getPrinter() != null && false) { //<-- remove false statement
            boolean noRecentRequests = requestInterceptor.hasNoRecentRequests();
            if (!noRecentRequests) {
                try {
                    acquireStructureReadLock();
                    System.out.println("exporting:"+ System.currentTimeMillis());
                    user.getPrinter().export();
                }  catch (ProcessingException e) {
                    failureMessage = e.getMessage();
                    failure = true;
                } catch (IOException e) {
                    failureMessage = new PrintException().getMessage();
                    failure = true;

                } finally {
                    releaseStructureReadLock();
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
