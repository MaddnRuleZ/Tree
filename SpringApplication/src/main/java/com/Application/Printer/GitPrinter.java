package com.Application.Printer;

import com.Application.Exceptions.UnknownElementException;
import com.Application.User;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;

public class GitPrinter extends Printer {
    /**
     *  user that holds information of LaTeX-Project
     */
    private User user;
    /**
     * url to the git repository
     */
    private String url;
    /**
     * username for the git repository
     */
    private String username;
    /**
     * password for the git repository
     */
    private String password;

    /**
     * path to the folder where the git repository should be cloned
     */
    private String path;

    public GitPrinter(User user, String overleafUrl, String username, String password, String filePath) {
        this.user = user;
        this.url = overleafUrl;
        this.username = username;
        this.password = password;
        this.path = filePath;
    }

    /**
     * path has to contain a path that does not exi
     *
     */
    public void cloneRepository() {
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
        try {
            Git.cloneRepository()
                    .setURI(this.url)
                    .setDirectory(new File(this.path))
                    .setCredentialsProvider(credsProvider)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void cloneOrUpdateRepository() {
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
        File repositoryPath = new File(this.path);

        if (repositoryPath.exists() && repositoryPath.isDirectory()) {
            try {
                Git git = Git.open(repositoryPath);
                git.pull().setCredentialsProvider(credsProvider).call();
                System.out.println("Repository updated successfully.");
            } catch (IOException | GitAPIException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Git.cloneRepository()
                        .setURI(this.url)
                        .setDirectory(repositoryPath)
                        .setCredentialsProvider(credsProvider)
                        .call();
                System.out.println("Repository cloned successfully.");
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
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


    @Override
    public void export() throws IOException, UnknownElementException {
        //TODO

    }
}
