package com.Application.Printer;

import java.io.IOException;

/**
 * provides the functionality to initialize pushes to storage all time seconds
 */
public class Clock implements Runnable {

    private Thread clockThread;
    private final int sleepTime = 1000;
    private Printer printer;

    public Clock(Printer printer) {
        this.printer = printer;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
                //TODO printer.export();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setClockThread(Thread clockThread) {
        this.clockThread = clockThread;
    }

    public Thread getClockThread() {
        return clockThread;
    }
}
