package com.application;

import com.application.command.LockManager;
import com.application.printer.AutoExport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.LinkedList;
import java.util.Queue;

/**
 * preprocesses requests and logs their timestamps to check if there were no incoming requests for a certain time
 * only requests that are processed by the \api endpoint are logged {@link  ApplicationConfig}
 * used {@link AutoExport}
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * queue that holds the timestamps of the received requests
     */
    private final Queue<Long> requestTimestamps = new LinkedList<>();

    /**
     * time threshold in milliseconds after which a request is considered old
     * and is removed from the queue
     * By Default the threshold is set to 1 minute
     * {@link AutoExport} sets the threshold if initialized
     */
    private long timeThresholdInMilliseconds = 60 * 1000;

    /**
     * shows if a request came in since the last time it was resetted {@link AutoExport}
     */
    private boolean changes = false;

    private final LockManager lockManager;

    public RequestInterceptor() {
        this.lockManager = LockManager.getInstance();
    }



    /**
     * adds the current timestamp to the queue and removes old timestamps
     * @param request the request
     * @param response the response
     * @param handler the handler
     * @return true if the request is valid
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long currentTimestamp = System.currentTimeMillis();
        requestTimestamps.add(currentTimestamp);
        removeOldTimestamps();
        changes = true;
        return true;
    }

    /**
     * removes old timestamps from the queue
     */
    private void removeOldTimestamps() {
        long currentTimestamp = System.currentTimeMillis();
        while (!requestTimestamps.isEmpty() && (currentTimestamp - requestTimestamps.peek()) > timeThresholdInMilliseconds) {
            requestTimestamps.remove();
        }
    }

    /**
     * checks if there are no recent requests
     * @return true if there are no recent requests
     */
    public boolean hasNoRecentRequests() {
        return requestTimestamps.isEmpty();
    }
    public boolean hasChanges() {
        this.lockManager.acquireInterceptorReadLock();
        boolean changes = this.changes;
        this.lockManager.releaseInterceptorReadLock();
        return changes;
    }
    /**
     * resets changes to false
     */
    public void resetChanges() {
        this.lockManager.acquireInterceptorWriteLock();
        this.changes = false;
        this.lockManager.releaseInterceptorWriteLock();
    }

    public long getTimeThresholdInMilliseconds() {
        return timeThresholdInMilliseconds;
    }

    public void setTimeThresholdInMilliseconds(long timeThresholdInMilliseconds) {
        this.timeThresholdInMilliseconds = timeThresholdInMilliseconds;
    }
}