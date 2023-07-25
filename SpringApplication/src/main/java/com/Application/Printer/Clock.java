package com.Application.Printer;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Exceptions.ProcessingException;

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
    private final int sleepTime = 50000;
    /**
     * tree structure to LaTeX-Code printer
     */
    private Printer printer;

    /**
     * message to display if an error occurs
     */
    String failureMessage = "Error while exporting the tree";

    /**
     * true if an error occurs
     */
    boolean failure = false;


    /**
     * creates a new clock
     * @param printer  to LaTeX-Code
     */
    public Clock(Printer printer) {
        this.printer = printer;
    }

    /**
     * starts the clock
     * exports changes to storage all time seconds
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
                acquireStructureReadLock();
                printer.export();
            } catch (InterruptedException | IOException e) {
                failureMessage = "Etwaige Änderungen konnten nicht gespeichert werden. \n " +
                        "Sollten Sie das LaTeX-Projekt aus einer Datei geladen haben, kann es sein dass der LaTeX-Code dennoch in einer temporären Datei gespeichert wurde. \n";
                failure = true;
                break;
            }  catch (ProcessingException e) {
                failureMessage = e.getMessage();
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
