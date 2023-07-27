package com.application.Command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.LinkedList;
import java.util.Queue;

/**
 * preprocesses requests and logs their timestamps to check if there were no incoming requests for a certain time
 * only requests that are processed by the \api endpoint are logged (see ApplicationConfig)
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
     * At the moment, the threshold is set to 2 minutes
     */
    private final long timeThresholdInMilliseconds = 2 * 60 * 1000;

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
}