package com.Application.Printer;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Exceptions.ProcessingException;
import com.Application.User;

import java.io.IOException;

/**
 * provides the functionality to initialize pushes to storage all time seconds
 */
public class Clock implements Runnable, ILocks {
    /**
     * thread that runs the clock
     */
    private Thread clockThread;
    /**
     * time to sleep between pushes
     */
    private final int sleepTime = 1000;
    /**
     * tree structure to LaTeX-Code printer
     */
    private Printer printer;

    /**
     * message to display if an error occurs
     */
    String failureMessage = "Error while exporting the tree";

    boolean failure = false;


    public Clock(Printer printer) {
        this.printer = printer;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
                acquireStructureReadLock();
                printer.export();
            } catch (InterruptedException | IOException e) {
                failure = true;
                break;
            } finally {
                releaseStructureReadLock();
            }
        }
    }

    public void setClockThread(Thread clockThread) {
        this.clockThread = clockThread;
    }

    public Thread getClockThread() {
        return clockThread;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public boolean isFailure() {
        return failure;
    }
}
