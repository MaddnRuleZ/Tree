package com.application;

import java.io.IOException;

/**
 * Responsible for automatically starting the browser and opening the tab
 */
public class Tools {


    /**
     * Start a Browser -instance of one of the Following BrowserTypes
     *
     * On Error print the Error
     */
    public static void openBrowserInstance() {
        String url = "http://localhost:8080";

        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {
            if (os.contains("win")) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                String[] browsers = {"xdg-open", "google-chrome", "firefox", "mozilla", "konqueror", "opera", "epiphany", "lynx"};
                boolean opened = false;
                for (String browser : browsers) {
                    try {
                        rt.exec(new String[] {browser, url});
                        opened = true;
                        break;
                    } catch (IOException e) {
                        // ignore Browser Starting if no suitable found
                    }
                }
                if (!opened) {
                    System.out.println("No suitable browser found.");
                }
            } else {
                System.out.println("Unsupported operating system.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
