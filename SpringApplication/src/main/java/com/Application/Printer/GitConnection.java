package com.Application.Printer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AUCHTUNG STILL OFFLINE UND STÜRZT TEILWEISE AB !
 *
 * Caution:
 * renaming the repo
 * renaming files, . . . will inevitably crash the dependencies !
 *
 * Logging in is still a mystey ...
 * apparently pops 1x up than saves its configuration?
 *
 * TODO LIST:
 * alle dirs . . . müssen vor innit gecheckt werden -> Fehlerpotential und wenig möglichkeiten zu überüfung
 * liste an common Exit Codes
 *
 */
public class GitConnection {
    private String OVERLEAF_URL;
    private final String ROOT_WORKING_DIR;
    private final String WORKING_DIR;
    private final String OVERLEAF_DOCUMENT;
    private final String OVERLEAF_CODE;
    private final String username;
    private final String p4ssw0rd;

    public GitConnection(String workingDir, String url, String document, String username, String p4ssw0rd) {
        this.ROOT_WORKING_DIR = workingDir;
        this.OVERLEAF_CODE = extractAlphanumericCode(url);
        this.WORKING_DIR = ROOT_WORKING_DIR + OVERLEAF_CODE;
        this.OVERLEAF_URL = url;
        this.OVERLEAF_DOCUMENT = document;
        this.username = username;
        this.p4ssw0rd = p4ssw0rd;

    }

    /**

     *
     */
    public boolean cloneAndInnitGitConnection() {
        System.out.println("Cloning Remote Connection. . .");
        int exit1 = executeCommand("cmd.exe", "/c", "cd", ROOT_WORKING_DIR, "&&", "git", "clone", OVERLEAF_URL);
        if (exit1 != 0) {
            System.out.println("ERROR IN CLONING: EXIT CODE:" + exit1);
            return false;
        }

        System.out.println("Setting up remote connection");
        int exit2 = executeCommand("cmd.exe", "/c", "cd", WORKING_DIR , "&&", "git", "remote", "add", "overleaf", OVERLEAF_URL);
        if (exit2 != 0) {
            System.out.println("ERROR IN SETTING REMOTE: EXIT CODE:" + exit2);
            return false;
        }
        return true;
    }

    /**
     *
     * @param commitMessage
     */
    public void commitAndPush(String commitMessage) {
        System.out.println("Pushing Files to master w/ Message" + commitMessage);
        int x = executeCommand("cmd.exe", "/c", "cd", "C:\\Users\\xmadd\\Desktop\\autoTesting\\64b430167d4b3be6afb4389c\\", "&&",
                "git", "add", "main.tex", "&&",
                "git", "commit", "-m", commitMessage, "&&",
                "git", "push", "origin", "master");

        System.out.println(x);
    }


    /**
     * Execute a Command in the CMD shell
     *
     * @param command command[s] to execture
     * @return returns the exit code of the command
     */
    private int executeCommand(String... command) {
        int exitCode = -1;
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            exitCode = process.waitFor();
            return exitCode;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return exitCode;
        }
    }

    /**
     * This method extracts the alphanumeric code from the end of the input string.
     *
     * @param inputString The input string from which the code needs to be extracted.
     * @return The extracted alphanumeric code, or null if no match is found.
     */
    private String extractAlphanumericCode(String inputString) {
        String pattern = "[a-zA-Z0-9]+$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(inputString);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }
}
