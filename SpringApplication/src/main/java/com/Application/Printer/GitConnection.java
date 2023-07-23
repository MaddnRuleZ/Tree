package com.Application.Printer;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
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

    public void cloneRepository(String repoUrl, String localPath, String username, String password) {
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(username, password);

        try {
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(new File(localPath))
                    .setCredentialsProvider(credsProvider)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void pushChanges(String localPath, String username, String password) {
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(username, password);

        try {
            Git git = Git.open(new File(localPath));
            git.push()
                    .setCredentialsProvider(credsProvider)
                    .call();
        } catch (Exception e) {
            e.printStackTrace();
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
